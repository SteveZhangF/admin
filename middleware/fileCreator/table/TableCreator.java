package com.oncore.middleware.fileCreator.table;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.model.TableElement;
import com.oncore.middleware.model.file.Report;
import com.oncore.middleware.model.file.ReportField;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 4/7/16.
 */

@Component
public class TableCreator {

    Log log = LogFactory.getLog(TableCreator.class);
    @Autowired
    DataSource dataSource;
    @Autowired
    @Qualifier("createSchemaFileCreator")
    CreateSchemaFileCreator createSchemaFileCreator;

    @Autowired
    @Qualifier("updateSchemaFileCreator")
    UpdateSchemaFileCreator updateSchemaFileCreator;

    public void createOrUpdateTable(TableElement tableElement) {
        File schemaFile = null;
        if (!checkTableExist(tableElement.getTableName())) {
            try {
                schemaFile = createSchemaFileCreator.createFile(tableElement);
                if (schemaFile != null) {
                    executeScript(schemaFile);
                }
            } catch (IOException e) {
                log.error("Error while creating create schema file", e);
            } catch (TemplateException e) {
                log.error("Error while creating create schema template ", e);
            }
        } else {
            List<ColumnObject> columnObjects = checkTableExistingColumn(tableElement.getTableName());
            Map map = new HashMap<>();
            map.put("oldTableColumns", columnObjects);
            map.put("newTable", tableElement);
            try {
                schemaFile = updateSchemaFileCreator.createFile(map);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(schemaFile));
                String tmp = "";
                StringBuffer stringBuffer = new StringBuffer();
                while ((tmp = bufferedReader.readLine()) != null) {
                    stringBuffer.append(tmp);
                }
                String[] scriptArray = stringBuffer.toString().split(";");
                for (String script : scriptArray) {
                    if (!script.trim().equals("")) {
                        executeScript(script);
                    }
                }
            } catch (IOException e) {
                log.error("Error while creating update schema file", e);
            } catch (TemplateException e) {
                log.error("Error while creating update schema template", e);
            }
        }

    }


    /**
     * execute a script from string
     */
    public void executeScript(String schema) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            log.info("executing schema  " + schema);
            connection.createStatement().executeUpdate(schema);
        } catch (SQLException e) {
            log.error("Error while executing schema ", e);
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                log.error("Error while closing connection ", e);
            }
        }
    }

    /**
     * execute schema from a file
     */
    public void executeScript(File file) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String buf = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((buf = bufferedReader.readLine()) != null) {
                stringBuffer.append(buf);
                stringBuffer.append("\n");
            }
            bufferedReader.close();
            executeScript(stringBuffer.toString());
        } catch (IOException e) {
            log.error("Error while reading schema file " + file.getAbsolutePath(), e);
        }
    }

    //entity_Employee_1460184015908_trigger.sql
    public void createTrigger(File file) throws SQLException, IOException {
        String dropTrigger = "DROP TRIGGER IF EXISTS " + file.getName().replace("_trigger.sql","_update").trim() + ";";
        executeScript(dropTrigger);
        executeScript(file);
    }

    @Autowired
    CommonConfigure commonConfigure;
    @Autowired
    TriggleFileCreator triggleFileCreator;
    public void addTrigger(Report report) throws SQLException {

        File file = null;
        for (ReportField field : report.getFields()) {
            String reportTableName = report.getTableName();
            String updateSQL = "\n update "+reportTableName+" set path=null ";
            String endSQL = " where "+reportTableName+".";

            if (field.isIfRelatedField()) {
                log.info("adding trigger for "+field.getName());
                String triggleFileName = field.getRelatedField().getTableName() + "_trigger.sql";
                file = new File(commonConfigure.getBaseDir() + "/" + commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + field.getRelatedField().getTableName() + "/" + triggleFileName);
                try {
                    if (!file.exists()) {
                        triggleFileCreator.createFile(field.getRelatedField().getTableName());
                    }
                    BufferedReader  fileReader = new BufferedReader(new FileReader(file));
                    String buf = "";
                    StringBuffer stringBuffer = new StringBuffer();
                    while((buf=fileReader.readLine())!=null){
                        stringBuffer.append(buf);
                        stringBuffer.append("\n");
                    }
                    fileReader.close();
                    String sql = updateSQL+ endSQL+field.getName()+" = new.id; \n end";
                    String result = stringBuffer.toString().replace("end",sql);
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(result);
                    fileWriter.flush();
                    fileWriter.close();
                    createTrigger(file);
                } catch (IOException e) {
                    log.error("Error while adding trigger to "+field.getRelatedField().getTableName(),e);
                } catch (TemplateException e) {
                    log.error("Error while creating trigger to "+field.getRelatedField().getTableName(),e);
                }

            }
        }
    }


    private List<ColumnObject> checkTableExistingColumn(String tableName) {
        List<ColumnObject> list = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet colRet = null;
            colRet = databaseMetaData.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                String columnType = colRet.getString("TYPE_NAME");
                int datasize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
                ColumnObject columnObject = new ColumnObject(columnName, columnType, datasize, digits, nullable);
                System.out.println(columnObject);
                list.add(columnObject);
            }
        } catch (SQLException e) {
            log.error("Error while reading column information ", e);
        }
        return list;
    }


    /**
     * check if a table existing
     *
     * @param tableName
     */
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
            log.error("Error while checking table status ", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
