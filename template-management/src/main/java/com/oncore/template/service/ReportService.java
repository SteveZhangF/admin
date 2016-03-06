package com.oncore.template.service;


import com.oncore.template.model.file.Report;
import com.oncore.template.transfd.report.CreateReportRequest;
import com.oncore.template.transfd.report.ReportResponse;

import java.util.List;

/**
 * Created by steve on 1/22/16.
 */
public interface ReportService {
    ReportResponse createReportFromRequest(CreateReportRequest reportRequest);
    Report updateReportFromRequest(Report report);
    ReportResponse getReport(String id);

    void deleteReport(String id);

    List<Report> listReports();

    List<Report> listReportsUnderFolder(String folderId);

}
