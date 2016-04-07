package com.oncore.template.transfd.report;

import com.oncore.template.model.file.ReportField;
import com.oncore.template.transfd.ElementResponse;
import com.oncore.template.transfd.entities.FieldResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by steve on 1/22/16.
 */
@XmlRootElement
public class ReportFieldResponse extends ElementResponse {

    private String fieldType;

    private int length;

    private boolean ifNull;

    private boolean ifRelatedField;

    private FieldResponse relatedField;

    public ReportFieldResponse(ReportField element) {
        super(element);
        this.setType("report_field");
        this.ifNull = element.isIfNull();
        this.ifRelatedField = element.isIfRelatedField();
        if(element.getRelatedField()!=null){
            this.relatedField = new FieldResponse(element.getRelatedField());
        }
        this.length = element.getLength();
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isIfNull() {
        return ifNull;
    }

    public void setIfNull(boolean ifNull) {
        this.ifNull = ifNull;
    }

    public boolean isIfRelatedField() {
        return ifRelatedField;
    }

    public void setIfRelatedField(boolean ifRelatedField) {
        this.ifRelatedField = ifRelatedField;
    }

    public FieldResponse getRelatedField() {
        return relatedField;
    }

    public void setRelatedField(FieldResponse relatedField) {
        this.relatedField = relatedField;
    }
}
