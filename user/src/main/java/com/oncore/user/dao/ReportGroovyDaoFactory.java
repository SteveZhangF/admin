package com.oncore.user.dao;

import com.oncore.common.groovy.GBaseDao;
import com.oncore.template.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by steve on 3/9/16.
 */
/**
 * all user things are moved to user project
 *
 * */
@Component("reportGroovyDaoFactory")
public class ReportGroovyDaoFactory extends GroovyDaoFactory {

    @Autowired
    ReportService reportService;

    @Override
    public GBaseDao getDao(String id) {
        String reportDaoName = reportService.getReportTableName(id);
        String daoBeanName = reportDaoName + "_Dao";
        GBaseDao gBaseDao = (GBaseDao) this.applicationContext.getBean(daoBeanName);
        return gBaseDao;
    }
}
