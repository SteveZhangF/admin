package com.oncore.web.init;

import com.oncore.data.TableBuilder;
import com.oncore.template.model.Entity;
import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import com.oncore.template.service.EntityService;
import com.oncore.template.service.ReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by steve on 3/7/16.
 */
@Service
public class DynamicTableInitor implements
        ApplicationListener<ContextRefreshedEvent> {
    TableBuilder tableBuilder;

    EntityService entityService;

    Log log = LogFactory.getLog(DynamicTableInitor.class);
    ReportService reportService;
    @Autowired
    public DynamicTableInitor(EntityService entityService,ReportService reportService, TableBuilder tableBuilder) {
        this.entityService = entityService;
        this.reportService = reportService;
        this.tableBuilder = tableBuilder;

//        init();
    }

    public void init() {
        tableBuilder.init();
        log.info("start init entity tables...");
        List<Entity> list = entityService.loadAll();
        for (Entity entity : list) {
            if (entity.getFields().size() == 0)
                continue;
            log.info("initing entity " + entity.getTableName() + "...");
            try {
                tableBuilder.createMappingFile(entity);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("error while init table " + entity.getTableName());
            }
        }

        List<Report> reports = reportService.loadAll();
        for(Report report:reports){
            ReportField field = new ReportField();
            field.setName("generated");
            field.setFieldType("boolean");
            report.addField(field);
            try {
                tableBuilder.createMappingFile(report);
            } catch (InterruptedException e) {
                log.error("error while init table " + report.getTableName());
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        init();
        if (event.getApplicationContext().getParent() == null) {
//            init();
        }
    }
}
