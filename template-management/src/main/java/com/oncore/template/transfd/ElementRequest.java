package com.oncore.template.transfd;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by steve on 1/20/16.
 */
public class ElementRequest {
    @NotNull
    @Valid
    @Length(max = 32,min = 4)
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
