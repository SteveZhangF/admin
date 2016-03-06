package com.oncore.template.service;

import com.oncore.common.exception.BadRequestException;
import com.oncore.template.dao.FieldDao;
import com.oncore.template.dao.ReportDao;
import com.oncore.template.dao.ReportFieldDao;
import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.model.Field;
import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import com.oncore.template.transfd.report.CreateReportRequest;
import com.oncore.template.transfd.report.ReportFieldRequest;
import com.oncore.template.transfd.report.ReportResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/22/16.
 */
@Service
@Transactional
public class ReportServiceImpl extends BaseGenericServiceImpl<Report, String> implements ReportService {

    private static Log logger = LogFactory.getLog(ReportServiceImpl.class);

    @Autowired
    public ReportServiceImpl(Validator validator) {
        super(validator);
    }



    @Autowired
    ReportDao reportDao;
    @Autowired
    ReportFieldDao reportFieldDao;
    @Autowired
    FieldDao fieldDao;


    @Override
    public ReportResponse createReportFromRequest(CreateReportRequest reportRequest) {
        validate(reportRequest);
        Long now = new Date().getTime();
        Report report = new Report();
        String tableName = "report_" + reportRequest.getName() + "_" + now;
        report.setTableName(tableName);
        report.setHbmPath(report.getName() + "/" + report.getTableName());
        report.setName(reportRequest.getName());
        report.setDescription(reportRequest.getDescription());
        report.setDeleted(false);

        report.setContent(reportRequest.getContent());


        for (ReportFieldRequest fieldRequest : reportRequest.getFields()) {
            ReportField field = new ReportField();
            field.setName(fieldRequest.getName());
            field.setDescription(fieldRequest.getDescription());
            field.setLength(fieldRequest.getLength());
            field.setFieldType(fieldRequest.getFieldType());
            field.setIfNull(fieldRequest.isIfNull());
            field.setIsRelatedField(fieldRequest.isRelatedField());
            if (fieldRequest.isRelatedField() ) {
                Field relatedField = null;
                if(fieldRequest.getRelatedField() == null || (relatedField =fieldDao.get(fieldRequest.getRelatedField())) == null){
                    throw new BadRequestException("related field can not be null or not found");
                }
                field.setRelatedField(relatedField);
            }
            report.addField(field);
        }
        reportDao.save(report);
        for (ReportField field : report.getFields()) {
            field.setDeleted(false);
            field.setReport(report);
            reportFieldDao.save(field);
        }
//TODO
//        iTableCreator.createTable(report);
        ReportResponse reportResponse = new ReportResponse(report);
   //TODO     reportResponse.setContent(reportTemplateFileMaker.getGeneratedReportTemplatePath(report));
        return reportResponse;
    }

    @Override
    public Report updateReportFromRequest(Report report) {
        return null;
    }

    @Override
    public ReportResponse getReport(String id) {
        Report report = reportDao.get(id);
        if (report == null) {
            logger.error("can't find report id=" + id);
            throw new ElementNotFoundException("report");
        }
        logger.info("found report id=" + report.getId() + " name=" + report.getName());
        ReportResponse reportResponse = new ReportResponse(report);
    //TODO    reportResponse.setContent(reportTemplateFileMaker.getGeneratedReportTemplatePath(report));
        return reportResponse;
    }

    @Override
    public List<Report> listReports() {
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", false);
        String[] fields = new String[]{"id", "name", "createTime", "updateTime", "description", "hbmPath", "tableName"};
        List reports = reportDao.getListbyFieldAndParams(fields, map);
        return reports;
    }

    @Override
    public void deleteReport(String id) {
        reportDao.deleteReport(id);
    }

    @Override
    public List<Report> listReportsUnderFolder(String folderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", false);
        map.put("folderId",folderId);
        String[] fields = new String[]{"id", "name", "createTime", "updateTime", "description", "hbmPath", "tableName"};
        List reports = reportDao.getListbyFieldAndParams(fields, map);
        return reports;
    }
}
