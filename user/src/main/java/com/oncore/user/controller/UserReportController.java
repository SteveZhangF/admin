package com.oncore.user.controller;

import com.oncore.common.controller.BaseController;
import com.oncore.common.groovy.GBaseDao;
import com.oncore.template.controller.ReportResource;
import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import com.oncore.template.service.ReportService;
import com.oncore.user.dao.GroovyDaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 3/9/16.
 */
/**
 * all user things are moved to user project
 *
 * */
@RestController
public class UserReportController extends BaseController {

    @Autowired
    @Qualifier("reportGroovyDaoFactory")
    GroovyDaoFactory groovyDaoFactory;

    @RequestMapping(value = "/user/reports/{report_id}/record/")
    public ResponseEntity listReport(@PathVariable("report_id") String report_id) throws SQLException {
        GBaseDao dao = groovyDaoFactory.getDao(report_id);
        List list = dao.list("123");
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @RequestMapping(value = "/user/reports/{report_table_id}/desc/")
    public ResponseEntity getReportDescription(@PathVariable("report_table_id") String report_table_id){
        Report report = reportService.get(report_table_id);
        return new ResponseEntity(report,HttpStatus.OK);
    }

    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/user/reports/{report_table_id}/record/{report_id}/")
    public ResponseEntity getReport(@PathVariable("report_table_id") String report_table_id, @PathVariable("report_id") int report_id) throws SQLException {
        GBaseDao dao = groovyDaoFactory.getDao(report_table_id);
        Map list = dao.get(report_id, "123");
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/reports/{report_id}/record/", method = RequestMethod.POST)
    public ResponseEntity insertReport(@PathVariable("report_id") String report_id, @RequestBody Map map) throws SQLException {
        GBaseDao dao = groovyDaoFactory.getDao(report_id);
        dao.insert(map, "123");
        return new ResponseEntity(HttpStatus.OK);
    }
}




