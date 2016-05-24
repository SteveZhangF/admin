package com.oncore.template.transfd.entities;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by steve on 1/20/16.
 */
@XmlRootElement
public class UpdateEntityRequest extends CreateEntityRequest {
    @NotNull
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
