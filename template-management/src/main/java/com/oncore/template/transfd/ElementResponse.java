package com.oncore.template.transfd;


import com.oncore.template.model.Element;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by steve on 1/20/16.
 */
@XmlRootElement
public class ElementResponse {

    private String id;
    private Timestamp createTime;
    private Timestamp updateTime;
    private boolean deleted;
    private String name;
    private String description;
    private String module_id;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public ElementResponse(Element element){
        this.id = element.getId();
        this.createTime = element.getCreateTime();
        this.updateTime = element.getUpdateTime();
        this.deleted = element.isDeleted();
        this.name = element.getName();
        this.description = element.getDescription();
        this.module_id = element.getModule_id();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

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
