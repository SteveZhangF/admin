package com.oncore.template.service;

import com.oncore.data.TableBuilder;
import com.oncore.data.file.ReportTemplateBuilder;
import com.oncore.data.file.downloader.DownLoader;
import com.oncore.template.dao.FieldDao;
import com.oncore.template.dao.ReportDao;
import com.oncore.template.dao.ReportFieldDao;
import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.helper.ReportGenerator;
import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import com.oncore.template.transfd.report.CreateReportRequest;
import com.oncore.template.transfd.report.ReportResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.io.IOException;
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
    @Autowired
    TableBuilder tableBuilder;
    @Autowired
    ReportTemplateBuilder reportTemplateBuilder;

    @Autowired
    @Qualifier(value = "awsDownloader")
    DownLoader downLoader;

    @Override
    protected void validate(Object request) {
        super.validate(request);
    }

    @Override
    public void delete(Report entity) {
        reportDao.delete(entity);
    }

    @Override
    public Report get(String id) {
        return reportDao.get(id);
    }

    @Override
    public Report load(String id) {
        return reportDao.load(id);
    }

    @Override
    public List<Report> loadAll() {

        return reportDao.loadAll();
    }

    @Override
    public void save(Report entity) {
        reportDao.save(entity);
    }

    @Override
    public void saveOrUpdate(Report entity) {
        reportDao.saveOrUpdate(entity);
    }

    @Override
    public void update(Report entity) {
        reportDao.update(entity);
    }

    @Override
    public Report buildReport(Report report, String content) {

        return null;
    }

    @Autowired
    ReportGenerator reportGenerator;

    @Override
    public ReportResponse createReportFromRequest(String folderId, CreateReportRequest reportRequest) {
        validate(reportRequest);
        Long now = new Date().getTime();
        Report report = new Report();
        String tableName = "report_" + reportRequest.getName().replace(" ", "_") + "_" + now;
        report.setTableName(tableName);
        report.setName(reportRequest.getName());
        report.setHbmPath(report.getName().replace(" ", "_") + "/" + report.getTableName());
        report.setDescription(reportRequest.getDescription());
        report.setDeleted(false);
        report.setFolderId(folderId);
        report.setContent(reportRequest.getContent());


//        for (ReportFieldRequest fieldRequest : reportRequest.getFields()) {
//            ReportField field = new ReportField();
//            field.setName(fieldRequest.getName());
//            field.setDescription(fieldRequest.getDescription());
//            field.setLength(fieldRequest.getLength());
//            field.setFieldType(fieldRequest.getFieldType());
//            field.setIfNull(fieldRequest.isIfNull());
//            field.setIsRelatedField(fieldRequest.isRelatedField());
//            if (fieldRequest.isRelatedField() ) {
//                Field relatedField = null;
//                if(fieldRequest.getRelatedField() == null || (relatedField =fieldDao.get(fieldRequest.getRelatedField())) == null){
//                    throw new BadRequestException("related field can not be null or not found");
//                }
//                field.setRelatedField(relatedField);
//            }
//            report.addField(field);
//        }
//        reportDao.save(report);
        reportDao.save(report);
        reportGenerator.parseHTML(report, report.getContent());

//        for (ReportField field : report.getFields()) {
//            field.setDeleted(false);
//            field.setReport(report);
//            reportFieldDao.save(field);
//        }
        try {
            ReportField field = new ReportField();
            field.setName("generated");
            field.setFieldType("boolean");
            report.addField(field);
            tableBuilder.createMappingFile(report);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        String targetName = report.getTableName();
        reportTemplateBuilder.uploadReport(reportTemplateBuilder.new ReportElement(targetName, report.getContent()));
        ReportResponse reportResponse = new ReportResponse(report);
        return reportResponse;
    }

    @Override
    public String getReportTableName(String id) {
        return reportDao.getReportTableName(id);
    }

    @Override
    public Report updateReportFromRequest(CreateReportRequest reportRequest) {
        validate(reportRequest);
        Report reportOld = null;
        if (reportRequest.getId() == null || (reportOld = reportDao.get(reportRequest.getId())) == null) {
            throw new ElementNotFoundException("Report");
        }

        Long now = new Date().getTime();
        Report report = new Report();
        String tableName = "report_" + reportRequest.getName().replace(" ", "_") + "_" + now;
        report.setTableName(tableName);
        report.setName(reportRequest.getName());
        report.setHbmPath(report.getName() + "/" + report.getTableName());
        report.setDescription(reportRequest.getDescription());
        report.setDeleted(false);
        report.setFolderId(reportOld.getFolderId());
        report.setContent(reportRequest.getContent());
        reportDao.update(report);
        reportGenerator.parseHTML(report, report.getContent());

//        for (ReportField field : report.getFields()) {
//            field.setDeleted(false);
//            field.setReport(report);
//            reportFieldDao.save(field);
//        }
        try {
            ReportField field = new ReportField();
            field.setName("generated");
            field.setFieldType("boolean");
            report.addField(field);
            tableBuilder.createMappingFile(report);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        String targetName = report.getTableName();
        reportTemplateBuilder.uploadReport(reportTemplateBuilder.new ReportElement(targetName, report.getContent()));
        return report;


    }

    @Override
    public ReportResponse getReport(String id) {
        Report report = reportDao.get(id);
        if (report == null) {
            logger.error("can't find report id=" + id);
            throw new ElementNotFoundException("report");
        }
        logger.info("found report id=" + report.getId() + " name=" + report.getName());
        String content = null;
        try {
            content = downLoader.getReportContent(report.getTableName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        report.setContent(content);
        ReportResponse reportResponse = new ReportResponse(report);
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
        map.put("folderId", folderId);
        String[] fields = new String[]{"id", "name", "createTime", "updateTime", "description", "hbmPath", "tableName"};
        List reports = reportDao.getListbyFieldAndParams(fields, map);
        return reports;
    }


}
