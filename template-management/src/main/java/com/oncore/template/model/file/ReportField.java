package com.oncore.template.model.file;


import com.oncore.template.model.Element;
import com.oncore.template.model.Field;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by steve on 1/20/16.
 */
@Entity
@Table(name = "report_field")
public class ReportField extends Element {
    @ManyToOne
    private Report report;

    private String fieldType;

    private int length;

    private boolean ifNull;

    private boolean isRelatedField;

    @ManyToOne
    private Field relatedField;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Field getRelatedField() {
        return relatedField;
    }

    public void setRelatedField(Field relatedField) {
        this.relatedField = relatedField;
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

    public boolean isRelatedField() {
        return isRelatedField;
    }

    public void setIsRelatedField(boolean isRelatedField) {
        this.isRelatedField = isRelatedField;
    }
}
