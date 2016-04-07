package com.oncore.web.init;

import com.oncore.data.messagequeue.producer.ProducerService;
import com.oncore.template.model.Entity;
import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import com.oncore.template.service.EntityService;
import com.oncore.template.service.ReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by steve on 3/7/16.
 */


/**
 * invoked when the system boot up
 * */
@Service
public class DynamicTableInitor implements
        ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    @Qualifier(value = "tableBuilderMessageSender")
    ProducerService tableBuilder;

    EntityService entityService;

    Log log = LogFactory.getLog(DynamicTableInitor.class);
    ReportService reportService;

    @Autowired
    public DynamicTableInitor(EntityService entityService, ReportService reportService) {
        this.entityService = entityService;
        this.reportService = reportService;
    }

    /**
     * get all table element and send them to JMS queue so the middleware could
     * create or update the table things
     * */
    public void init() {
        log.info("start init entity tables...");
        List<Entity> list = entityService.loadAll();
        for (Entity entity : list) {
            if (entity.getFields().size() == 0)
                continue;
            log.info("initing entity " + entity.getTableName() + "...");
            tableBuilder.sendMessage(entity);
        }
        log.info("start init report tables...");
        List<Report> reports = reportService.loadAll();
        for (Report report : reports) {
            ReportField field = new ReportField();
            field.setName("generated");
            field.setFieldType("boolean");
            report.addField(field);
            tableBuilder.sendMessage(report);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        init();
        if (event.getApplicationContext().getParent() != null) {
            init();
        }
    }
}
