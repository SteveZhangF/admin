package com.oncore.template.transfd.entities;


import com.oncore.template.model.Entity;
import com.oncore.template.model.Field;
import com.oncore.template.transfd.ElementResponse;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 1/20/16.
 */
@XmlRootElement
public class EntityResponse extends ElementResponse {


    private List<FieldResponse> fields = new ArrayList<>();

    public EntityResponse(Entity entity){
        super(entity);
        this.setType("entity");
        for (Field field: entity.getFields()){
            fields.add(new FieldResponse(field));
        }

    }

    public List<FieldResponse> getFields() {
        return fields;
    }

    public void setFields(List<FieldResponse> fields) {
        this.fields = fields;
    }
}
