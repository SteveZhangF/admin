package com.oncore.template.transfd.entities;

import com.oncore.template.transfd.ElementRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by steve on 1/20/16.
 */
@XmlRootElement
public class CreateEntityRequest extends ElementRequest {

    @NotNull
    @Valid
    @Size(min = 1,max = 16)
    List<FieldRequest> fields;

    private boolean unique;

    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public List<FieldRequest> getFields() {
        return fields;
    }

    public void setFields(List<FieldRequest> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "CreateEntityRequest{" +
                "fields=" + fields +
                "} " + super.toString();
    }
}


