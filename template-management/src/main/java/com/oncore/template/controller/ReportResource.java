package com.oncore.template.controller;

import com.oncore.common.controller.BaseController;
import com.oncore.template.model.file.Report;
import com.oncore.template.service.ReportService;
import com.oncore.template.transfd.report.CreateReportRequest;
import com.oncore.template.transfd.report.ReportResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 1/22/16.
 */
@RestController
public class ReportResource extends BaseController {
    Log log = LogFactory.getLog(ReportResource.class);
    @Autowired
    ReportService reportService;

    // get all reports
    @RequestMapping(value = "/admin/reports/", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<ReportResponse> getReports() {
        this.checkUserContext();
        List<Report> list = reportService.listReports();
        List<ReportResponse> listResponse = new ArrayList<>();
        for (Report report : list) {
            listResponse.add(new ReportResponse(report));
        }
        return listResponse;
    }

    //get one report for admin to edit
    @RequestMapping(value = "/admin/reports/{id}/", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ReportResponse gerReport(@PathVariable("id") String reportId) {
        this.checkUserContext();
        return reportService.getReport(reportId);
    }

    //delete one report
    @RequestMapping(value = "/admin/reports/{id}/", method = RequestMethod.DELETE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("id") String id) {
        this.checkUserContext();
        reportService.deleteReport(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    //update one report
    @RequestMapping(value = "/admin/reports/{id}/", method = RequestMethod.PUT, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateReport(@PathVariable("id") String id, @RequestBody CreateReportRequest reportRequest) {
        this.checkUserContext();
        reportRequest.setId(id);
        reportService.updateReportFromRequest(reportRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}
