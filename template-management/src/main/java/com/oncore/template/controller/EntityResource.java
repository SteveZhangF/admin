/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.controller;

import com.oncore.common.controller.BaseController;
import com.oncore.template.model.Entity;
import com.oncore.template.service.EntityService;
import com.oncore.template.service.ModuleService;
import com.oncore.template.transfd.entities.EntityResponse;
import com.oncore.template.transfd.entities.UpdateEntityRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class EntityResource extends BaseController {

    Log log = LogFactory.getLog(EntityResource.class);
    @Autowired
    EntityService entityService;
    @Autowired
    ModuleService moduleService;

    /**
     * get all entity
     * */
    //    @RolesAllowed({"ROLE_USER"})
    @RequestMapping(value = "/admin/entities/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EntityResponse> getEntities() {
        checkUserContext();
        List<EntityResponse> list = entityService.listEntities();
        return list;
    }

    /**
     * update entity
     * */
    //    @RolesAllowed({"ROLE_USER"})
    @RequestMapping(value = "/admin/entities/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

    public EntityResponse updateEntity( @RequestBody UpdateEntityRequest request) {
        checkUserContext();
        return entityService.updateEntityFromRequest(request);
    }

    /**
     * get entity
     * */
    //    @RolesAllowed({"ROLE_USER"})
    @RequestMapping(value = "/admin/entities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getEntity(final @PathVariable("id") String entityId) {
        checkUserContext();
        return entityService.getEntity(entityId);
    }

    //    @RolesAllowed({"ROLE_USER"})
    /**
     *
     * delete the entity
     * */
    @RequestMapping(value = "/admin/entities/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(final @PathVariable("id") String id  ) {
        checkUserContext();
        entityService.deleteEntity(id);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @RolesAllowed({"ROLE_USER"})
//    @Path("/module/{moduleId}")
//    @GET
//    @RequestMapping(value = "/admin/entities/{id}", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
//
//    public List<EntityResponse> getEntityUnderModule(@Context SecurityContext context, @PathParam("moduleId") String moduleId) {
//        List<Entity> entities = entityService.getEntityUnderModule(moduleId);
//        List<EntityResponse> listResponse = entities.stream().map(EntityResponse::new).collect(Collectors.toList());
//        return listResponse;
//    }


}
