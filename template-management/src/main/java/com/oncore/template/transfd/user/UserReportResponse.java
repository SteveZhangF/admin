package com.oncore.template.transfd.user;

import com.oncore.template.model.file.Report;

/**
 * Created by steve on 3/30/16.
 */
public class UserReportResponse {

    public UserReportResponse(Report report){
        this.name = report.getName();
        this.tableName = report.getTableName();
        this.description = report.getDescription();
    }

    private String name;
    private String tableName;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
