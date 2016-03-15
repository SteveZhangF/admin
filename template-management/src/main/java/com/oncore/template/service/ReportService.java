package com.oncore.template.service;


import com.oncore.template.model.file.Report;
import com.oncore.template.transfd.report.CreateReportRequest;
import com.oncore.template.transfd.report.ReportResponse;

import java.util.List;

/**
 * Created by steve on 1/22/16.
 */
public interface ReportService extends IBaseGenericService<Report,String>{
    ReportResponse createReportFromRequest(String folderId,CreateReportRequest reportRequest);
    Report updateReportFromRequest(CreateReportRequest report);
    ReportResponse getReport(String id);
    String getReportTableName(String id);

    void deleteReport(String id);

    List<Report> listReports();

    List<Report> listReportsUnderFolder(String folderId);

    Report buildReport(Report report,String content);

}
