package com.oncore.template.dao;


import com.oncore.template.model.file.Report;

/**
 * Created by steve on 1/20/16.
 */
public interface ReportDao  extends IBaseGenericDao<Report,String> {
    void deleteReport(String id);
    String getReportTableName(String id);

    void setFolder(String folderId, String reportId);
}
