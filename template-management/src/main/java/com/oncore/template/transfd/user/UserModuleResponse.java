package com.oncore.template.transfd.user;

import com.oncore.template.model.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 3/30/16.
 */
public class UserModuleResponse {

    public UserModuleResponse(Module module){
        this.name = module.getName();
        this.description = module.getDescription();
    }

    public UserModuleResponse(Module module, List<UserFolderResponse> folders, List<UserEntityResponse> entities) {
        this(module);
        this.folders = folders;
        this.entities = entities;
    }


    private String name;
    private String description;

    private List<UserEntityResponse> entities = new ArrayList();
    private List<UserFolderResponse> folders = new ArrayList();

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

    public List getEntities() {
        return entities;
    }

    public void setEntities(List entities) {
        this.entities = entities;
    }

    public List getFolders() {
        return folders;
    }

    public void setFolders(List folders) {
        this.folders = folders;
    }
}
