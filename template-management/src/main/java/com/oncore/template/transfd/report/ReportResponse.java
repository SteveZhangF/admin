package com.oncore.template.transfd.report;


import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import com.oncore.template.transfd.ElementResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 1/22/16.
 */
@XmlRootElement
public class ReportResponse extends ElementResponse {



    private String content;

    private List<ReportFieldResponse> fields = new ArrayList<>();

    private String folderId;

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportResponse(Report element) {
        super(element);
        this.folderId = element.getFolderId();
        this.setType("report");
        for(ReportField field: element.getFields()){
            ReportFieldResponse fieldResponse = new ReportFieldResponse(field);
            this.fields.add(fieldResponse);
        }
        this.content = element.getContent();
    }

    public List<ReportFieldResponse> getFields() {
        return fields;
    }

    public void setFields(List<ReportFieldResponse> fields) {
        this.fields = fields;
    }
}
