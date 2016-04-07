package com.oncore.template.transfd.user;


import com.oncore.template.model.Entity;

/**
 * Created by steve on 3/30/16.
 */
public class UserEntityResponse {

    public UserEntityResponse(Entity entity){
        this.tableName = entity.getTableName();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }

    private String tableName;
    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
