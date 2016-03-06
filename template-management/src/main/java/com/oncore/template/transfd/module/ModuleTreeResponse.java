/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.transfd.module;

import com.oncore.template.model.Entity;
import com.oncore.template.model.Module;
import com.oncore.template.model.file.Folder;
import com.oncore.template.transfd.entities.EntityResponse;
import com.oncore.template.transfd.file_element.folder.FolderResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 3/2/16.
 */

public class ModuleTreeResponse extends ModuleResponse {

    private List<EntityResponse> entities;
    private List<FolderResponse> folders;

    public List<EntityResponse> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityResponse> entities) {
        this.entities = entities;
    }

    public List<FolderResponse> getFolders() {
        return folders;
    }

    public void setFolders(List<FolderResponse> folders) {
        this.folders = folders;
    }

    public ModuleTreeResponse(Module element,List<Entity> entitiesT,List<Folder> rootFoldersT) {
        super(element);
        entities = new ArrayList<>();
        folders = new ArrayList<>();
        for(Entity entity:entitiesT){
            entities.add(new EntityResponse(entity));
        }
        for(Folder folder: rootFoldersT){
            folders.add(new FolderResponse(folder));
        }
    }
}
