package com.oncore.template.transfd.report;


import com.oncore.template.transfd.ElementRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by steve on 1/22/16.
 */
@XmlRootElement
public class ReportFieldRequest extends ElementRequest {

    @NotNull
    @Valid
    private String fieldType;

    private int length;
    @NotNull
    @Valid
    private boolean ifNull;
    @NotNull
    @Valid
    private boolean isRelatedField;

    private String relatedField;

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

    public boolean isRelatedField() {
        return isRelatedField;
    }

    public void setIsRelatedField(boolean isRelatedField) {
        this.isRelatedField = isRelatedField;
    }

    public String getRelatedField() {
        return relatedField;
    }

    public void setRelatedField(String relatedField) {
        this.relatedField = relatedField;
    }
}
