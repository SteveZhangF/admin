/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.controller;

import com.oncore.common.controller.BaseController;
import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.model.Entity;
import com.oncore.template.model.Module;
import com.oncore.template.model.file.Folder;
import com.oncore.template.service.EntityService;
import com.oncore.template.service.FolderService;
import com.oncore.template.service.ModuleService;
import com.oncore.template.transfd.entities.CreateEntityRequest;
import com.oncore.template.transfd.entities.EntityResponse;
import com.oncore.template.transfd.file_element.folder.CreateRootFolderRequest;
import com.oncore.template.transfd.file_element.folder.FolderResponse;
import com.oncore.template.transfd.module.CreateModuleRequest;
import com.oncore.template.transfd.module.ModuleResponse;
import com.oncore.template.transfd.module.ModuleTreeResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 1/26/16.
 */
@RestController
public class ModuleResource extends BaseController {

    Log log = LogFactory.getLog(ModuleResource.class);
    @Autowired
    ModuleService moduleService;

    @RequestMapping(value = "/admin/modules/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModuleResponse> getModules( )   {
        this.checkUserContext();
        List<Module> list = moduleService.loadAll();
        List<ModuleResponse> listResponse = new ArrayList<>();
        for (Module module : list) {
            ModuleResponse response = new ModuleResponse(module);
            listResponse.add(response);
        }
        return listResponse;
    }

    @RequestMapping(value = "/admin/modules/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleResponse getModule(  @PathVariable("id") String id) {
        this.checkUserContext();
        Module entity = moduleService.getModule(id);
        return new ModuleResponse(entity);
    }

    @RequestMapping(value = "/admin/modules/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleResponse createModule(  @RequestBody CreateModuleRequest request) {
        checkUserContext();
        Module module = moduleService.createModuleFromRequest(request);
        ModuleResponse moduleResponse = new ModuleResponse(module);
        return moduleResponse;
    }


    @RequestMapping(value = "/admin/modules/{id}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleResponse updateModule(  @PathVariable("id") String id, @RequestBody CreateModuleRequest request) {
        this.checkUserContext();
        Module module = moduleService.updateModuleFromRequest(id, request);
        ModuleResponse moduleResponse = new ModuleResponse(module);
        return moduleResponse;
    }

    @Autowired
    EntityService entityService;

    @RequestMapping(value = "/admin/modules/{id}/entities/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EntityResponse> getEntitiesOfModule(  @PathVariable("id") String id) {
        this.checkUserContext();
        List<Entity> entityList = entityService.getEntitiesOfModule(id);
        List<EntityResponse> responses = new ArrayList<>();
        for (Entity entity : entityList) {
            responses.add(new EntityResponse(entity));
        }
        return responses;
    }


    @Autowired
    FolderService folderService;

    @RequestMapping(value = "/admin/modules/{id}/folders/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public List<FolderResponse> getFoldersOfModule( @PathVariable("id") String id) {
        this.checkUserContext();
        List<FolderResponse> folderList = folderService.getRootFolderUnderModule(id);
        return folderList;
    }


    @RequestMapping(value = "/admin/modules/{id}/folders/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderResponse createFolderUnderModule(  @PathVariable("id") String id, @RequestBody CreateRootFolderRequest rootFolderRequest)   {
        this.checkUserContext();
        rootFolderRequest.setModuleId(id);
        return folderService.createRootFolderUnderModule(rootFolderRequest);
    }

    @RequestMapping(value = "/admin/modules/{id}/entities/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse createEntityUnderModule(  @PathVariable("id") String id, @RequestBody CreateEntityRequest entityRequest)   {
        this.checkUserContext();
        Entity entity1 = entityService.createEntityFromRequest(id, entityRequest);
        EntityResponse entityResponse = new EntityResponse(entity1);
        return entityResponse;
    }


    @RequestMapping(value = "/admin/modules/{id}/tree/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ModuleTreeResponse getModuleTree( @PathVariable("id") String id) {
        this.checkUserContext();
        Module module = moduleService.getModule(id);
        if (module == null) {
            throw new ElementNotFoundException("module");
        }
        List<Folder> folderList = folderService.getRootFoldersUnderModule(id);
        List<Entity> entityList = entityService.getEntitiesOfModule(id);
        return new ModuleTreeResponse(module, entityList, folderList);
    }

}
