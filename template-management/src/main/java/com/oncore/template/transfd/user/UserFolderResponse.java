package com.oncore.template.transfd.user;

import com.oncore.template.model.file.Folder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 3/30/16.
 */
public class UserFolderResponse {

    public UserFolderResponse(Folder folder){
        this.name = folder.getName();
        this.description = folder.getDescription();
    }


    private String name;
    private String description;

    private List subFolders = new ArrayList();
    private List reports = new ArrayList();

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

    public List getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List subFolders) {
        this.subFolders = subFolders;
    }

    public List getReports() {
        return reports;
    }

    public void setReports(List reports) {
        this.reports = reports;
    }
}
