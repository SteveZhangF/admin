package com.oncore.data;

import com.oncore.common.configure.CommonConfigure;
import com.oncore.common.groovy.GBaseDao;
import com.oncore.common.model.TableElement;
import com.oncore.data.file.FileCreator;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by steve on 3/6/16.
 */
@Component
public class TableBuilder implements ApplicationContextAware {
    BlockingQueue<TableElement> preCreatingFileQueue = new LinkedBlockingQueue<>();
    BlockingQueue<File> preCreatingTableQueue = new LinkedBlockingQueue<>();
    BlockingQueue<TableElement> preCreatingGroovyFileQueue
            = new LinkedBlockingQueue<>();
    BlockingQueue<File> preRegisterGroovyDaoQueue
            = new LinkedBlockingQueue<>();

    private ExecutorService executorService;
    private ApplicationContext applicationContext;

    @Autowired
    public TableBuilder(CommonConfigure commonConfigure) {
        executorService = Executors.newFixedThreadPool(commonConfigure.getCreate_table_thread_number() * 3 + 1);
        for (int i = 0; i < commonConfigure.getCreate_table_thread_number() * 3; i = i + 3) {
            FileCreatorRunner creator = new FileCreatorRunner();
            TableCreatorRunner tableCreatorRunner = new TableCreatorRunner();
            GroovyCreatorConsumer groovyCreatorConsumer = new GroovyCreatorConsumer();
            executorService.submit(tableCreatorRunner);
            executorService.submit(creator);
            executorService.submit(groovyCreatorConsumer);
            System.out.println(i);
        }
    }

    public void createMappingFile(TableElement e) throws InterruptedException {
        preCreatingFileQueue.put(e);
    }

    @Autowired
    @Qualifier("groovyFileCreator")
    FileCreator groovyFileCreator;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        executorService.submit(new GroovyFactory(applicationContext));
        log.info("setting application context...");
    }

    Log log = LogFactory.getLog(TableBuilder.class);
    /**
     * create groovy dao consumer
     */
    class GroovyCreatorConsumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    TableElement element = preCreatingGroovyFileQueue.take();
                    File file = groovyFileCreator.createFile(element);
                    log.info("saving groovy file...");
                    preRegisterGroovyDaoQueue.put(file);
                    log.info("saved groovy file...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    DataSource dataSource;

    /**
     * create table customer
     */
    class TableCreatorRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    File file = preCreatingTableQueue.take();
                    createTable(file);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public void createTable(File file) throws SQLException {
            org.hibernate.cfg.Configuration conf = new org.hibernate.cfg.Configuration();
//        conf.configure("/META-INF/springHibernate.xml");
            Properties extraProperties = new Properties();
            extraProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            extraProperties.put("hibernate.hbm2ddl.auto", "update");
            extraProperties.put("hibernate.show_sql", "true");
            extraProperties.put("hibernate.format_sql", "true");
//        conf.addProperties(extraProperties);

            conf.addFile(file);

            org.hibernate.service.ServiceRegistry serviceRegistry = sessionFactory.getSessionFactoryOptions().getServiceRegistry();

            conf.addProperties(extraProperties);

            String tableName = file.getParentFile().getName();

            // if the table is not exists then create one
            if (!checkTableExist(tableName)) {
                SchemaExport export = new SchemaExport(conf, dataSource.getConnection());
                export.setOutputFile(file.getParentFile().getAbsolutePath() + "/" + tableName);
                export.create(false, true);
            } else {
                SchemaUpdate dbExport = new SchemaUpdate(serviceRegistry, conf);
                dbExport.setOutputFile(file.getParentFile().getAbsolutePath() + "/" + tableName);
                dbExport.execute(false, true);
            }
        }

        private boolean checkTableExist(String tableName) {
            Connection conn = null;
            try {
                conn = dataSource.getConnection();
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet rs = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * create file customer
     */
    @Autowired
    @Qualifier("hibernateMappingFileCreator")
    FileCreator fileCreator;

    @Autowired
    CommonConfigure commonConfigure;;
    class FileCreatorRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                TableElement element = null;
                try {
                    element = preCreatingFileQueue.take();
                    File file = fileCreator.createFile(element);
                    preCreatingTableQueue.put(file);
                    preCreatingGroovyFileQueue.put(element);

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    if (element != null) {
                        try {
                            preCreatingFileQueue.put(element);
                        } catch (InterruptedException e1) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    class GroovyFactory implements Runnable {

        private ApplicationContext applicationContext;


        private static final String REFRESH_CHECK_DELAY_STR = "org.springframework.scripting.support.ScriptFactoryPostProcessor.refreshCheckDelay";
        private static final String LANGUAGE = "org.springframework.scripting.support.ScriptFactoryPostProcessor.language";
        private static final String BEAN_CLASS_NAME = "org.springframework.scripting.groovy.GroovyScriptFactory";

        private DefaultListableBeanFactory beanFactory = null;

        public GroovyFactory(ApplicationContext context) {
            System.out.println(context);
            log.info("running GroovyFactory...");
            this.applicationContext = context;
            this.beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        }


        /**
         * add or update one groovy dao bean to context
         */
        public void addGroovyFile(File file) {
            GenericBeanDefinition bd = new GenericBeanDefinition();
            bd.setBeanClassName(BEAN_CLASS_NAME);
            bd.setAttribute(REFRESH_CHECK_DELAY_STR, 500);
            bd.setAttribute(LANGUAGE, "groovy");
            //here must use xiangdui path
            String path = file.getAbsolutePath().replace(commonConfigure.getBaseDir(),"");

            log.info("file path..."+path);
            bd.getConstructorArgumentValues().addIndexedArgumentValue(0, path);
            // register bean to context
            log.info("registering bd....");
            beanFactory.registerBeanDefinition(file.getParentFile().getName() + "_Dao", bd);
            log.info("registered bd....");
            try {
                GBaseDao dao = (GBaseDao) beanFactory.getBean(file.getParentFile().getName() + "_Dao");
                log.info("getting dao..." + dao.toString());
            }catch (Exception e){
                e.printStackTrace();
            }


        }


        @Override
        public void run() {
            while (true) {
                try {
                    File file = preRegisterGroovyDaoQueue.take();
                    log.info("reading groovy file...");
                    this.addGroovyFile(file);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
