package com.oncore.template.model.file;


import com.oncore.common.model.TableElement;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 1/14/16.
 */
@Entity
@Table(name = "report_file_element")
public class Report extends FileElement implements TableElement {

    private String tableName;

    private String hbmPath;

    private String templatePath;

    private String currentVersion;

    private String folderId;

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    @Transient
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @OneToMany(mappedBy = "report")
    private List<ReportField> fields = new ArrayList<>();


    public void addField(ReportField field){
        this.fields.add(field);
    }

    public List<ReportField> getFields() {
        return fields;
    }

    public void setFields(List<ReportField> fields) {
        this.fields = fields;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setHbmPath(String hbmPath) {
        this.hbmPath = hbmPath;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public String getHbmPath() {
        return this.hbmPath;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}
