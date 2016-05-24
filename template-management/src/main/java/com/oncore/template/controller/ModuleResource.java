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
import com.oncore.template.model.file.Report;
import com.oncore.template.service.EntityService;
import com.oncore.template.service.FolderService;
import com.oncore.template.service.ModuleService;
import com.oncore.template.service.ReportService;
import com.oncore.template.transfd.entities.CreateEntityRequest;
import com.oncore.template.transfd.entities.EntityResponse;
import com.oncore.template.transfd.file_element.folder.CreateRootFolderRequest;
import com.oncore.template.transfd.file_element.folder.FolderResponse;
import com.oncore.template.transfd.module.CreateModuleRequest;
import com.oncore.template.transfd.module.ModuleResponse;
import com.oncore.template.transfd.module.ModuleTreeResponse;
import com.oncore.template.transfd.user.UserEntityResponse;
import com.oncore.template.transfd.user.UserFolderResponse;
import com.oncore.template.transfd.user.UserModuleResponse;
import com.oncore.template.transfd.user.UserReportResponse;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

    /**
     * get all modules
     * */
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

    /**
     * get one module
     * */
    @RequestMapping(value = "/admin/modules/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleResponse getModule(  @PathVariable("id") String id) {
        this.checkUserContext();
        Module entity = moduleService.getModule(id);
        return new ModuleResponse(entity);
    }

    /**
     * create one module
     * */
    @RequestMapping(value = "/admin/modules/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleResponse createModule(  @RequestBody CreateModuleRequest request) {
        checkUserContext();
        Module module = moduleService.createModuleFromRequest(request);
        ModuleResponse moduleResponse = new ModuleResponse(module);
        return moduleResponse;
    }


    /**
     * update one module
     * */
    @RequestMapping(value = "/admin/modules/{id}/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModuleResponse updateModule(  @PathVariable("id") String id, @RequestBody CreateModuleRequest request) {
        this.checkUserContext();
        Module module = moduleService.updateModuleFromRequest(id, request);
        ModuleResponse moduleResponse = new ModuleResponse(module);
        return moduleResponse;
    }

    @Autowired
    EntityService entityService;

    /**
     * get entities under one module
     * */
    @RequestMapping(value = "/admin/modules/{id}/entities/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EntityResponse> getEntitiesOfModule(  @PathVariable("id") String id) {
        this.checkUserContext();
        List<EntityResponse> responses = entityService.getEntitiesOfModule(id);
        return responses;
    }


    @Autowired
    FolderService folderService;

    /**
     * get folders under one module
     *
     * */
    @RequestMapping(value = "/admin/modules/{id}/folders/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public List<FolderResponse> getFoldersOfModule( @PathVariable("id") String id) {
        this.checkUserContext();
        List<FolderResponse> folderList = folderService.getRootFolderUnderModule(id);
        return folderList;
    }

    /**
     * create one folder under module
     * */
    @RequestMapping(value = "/admin/modules/{id}/folders/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FolderResponse createFolderUnderModule(  @PathVariable("id") String id, @RequestBody CreateRootFolderRequest rootFolderRequest)   {
        this.checkUserContext();
        rootFolderRequest.setModuleId(id);
        return folderService.createRootFolderUnderModule(rootFolderRequest);
    }


    /**
     *
     * create entity under module
     * */
    @RequestMapping(value = "/admin/modules/{id}/entities/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse createEntityUnderModule(  @PathVariable("id") String id, @RequestBody CreateEntityRequest entityRequest)   {
        this.checkUserContext();
        log.info("creating entity "+ entityRequest.toString());
        return entityService.createEntityFromRequest(id, entityRequest);
    }


    /**
     * get the module tree for admin
     * */
    @RequestMapping(value = "/admin/modules/{id}/tree/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ModuleTreeResponse getModuleTree( @PathVariable("id") String id) {
        this.checkUserContext();
        Module module = moduleService.getModule(id);
        if (module == null) {
            throw new ElementNotFoundException("module");
        }
        List<FolderResponse> folderList = folderService.getRootFoldersUnderModule(id);
        List<EntityResponse> entityList = entityService.getEntitiesOfModule(id);
        return new ModuleTreeResponse(module, entityList, folderList);

    }

    /**
     * get module tree for user
     * */
    @RequestMapping(value = "/user/modules/{id}/tree/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public UserModuleResponse getModuleTreeForUser(@PathVariable("id") String id){
//        this.checkUserContext();

        Module module = moduleService.getModule(id);
        if (module == null) {
            throw new ElementNotFoundException("module");
        }

        UserModuleResponse userModuleResponse = new UserModuleResponse(module);

        List<Folder> folderList = folderService.getUserRootFolderUnderModule(id);
        List<Entity> entityList = entityService.getUserEntitiesOfModule(id);

        for(Folder folder: folderList){
            userModuleResponse.getFolders().add(getFoldersTree(folder));
        }
        for (Entity entity:entityList){
            userModuleResponse.getEntities().add(new UserEntityResponse(entity));
        }
        return userModuleResponse;
    }

    /**
     * get all modules for user
     * */
    @RequestMapping(value = "/user/modules/trees/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserModuleResponse> getUserModules(){
//        this.checkUserContext();
        List<Module> list =  moduleService.loadAll();
        List<UserModuleResponse> userModuleResponses = new ArrayList<>();
        for(Module m:list){
            UserModuleResponse userModuleResponse = new UserModuleResponse(m);

            List<Folder> folderList = folderService.getUserRootFolderUnderModule(m.getId());
            List<Entity> entityList = entityService.getUserEntitiesOfModule(m.getId());

            for(Folder folder: folderList){
                userModuleResponse.getFolders().add(getFoldersTree(folder));
            }
            for (Entity entity:entityList){
                userModuleResponse.getEntities().add(new UserEntityResponse(entity));
            }
            userModuleResponses.add(userModuleResponse);
        }

        return userModuleResponses;
    }


    @Autowired
    ReportService reportService;
    private UserFolderResponse getFoldersTree(Folder folder){
        UserFolderResponse userFolderResponse = new UserFolderResponse(folder);
        List<Report> reports = reportService.listReportsUnderFolder(folder.getId());
        for(Report report:reports){
            userFolderResponse.getReports().add(new UserReportResponse(report));
        }
        List<Folder> child = folderService.getUserChildFolder(folder.getId());
        for(Folder folder1:child){
            userFolderResponse.getSubFolders().add(getFoldersTree(folder1));
        }
        return userFolderResponse;
    }
}
