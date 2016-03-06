///*
// * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
// * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
// * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
// * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
// * Vestibulum commodo. Ut rhoncus gravida arcu.
// */
//
//package com.oncore.template.controller;
//
//import com.oncore.auth.resource.BaseResource;
//import com.oncore.auth.user.User;
//import com.oncore.common.exception.ApplicationRuntimeException;
//import com.oncore.common.exception.NullParametersException;
//import com.oncore.server.message.EntityException;
//import com.oncore.server.service.customized.CustomizedElementServiceFactory;
//import com.oncore.server.transfd.customizedRecord.CreateCustomizedRecordRequest;
//import com.oncore.server.transfd.customizedRecord.CustomizedRecordType;
//import com.oncore.server.transfd.customizedRecord.GetCustomizedRecordRequest;
//import com.oncore.server.transfd.customizedRecord.ListCustomizedRecordRequest;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.security.RolesAllowed;
//import javax.ws.rs.*;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.SecurityContext;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by steve on 1/22/16.
// */
//@Path("/user/entities")
//@Component
//@Produces({MediaType.APPLICATION_JSON})
//@Consumes({MediaType.APPLICATION_JSON})
//public class CustomizedResource extends BaseResource {
//    @Autowired
//    CustomizedElementServiceFactory customizedElementServiceFactory;
//    private static Log log = LogFactory.getLog(CustomizedResource.class);
//
//    @RolesAllowed({"ROLE_USER"})
//    @POST
//    public Response insertCustomizedRecord(@Context SecurityContext context, CreateCustomizedRecordRequest createCustomizedRecordRequest) {
//
//        if (createCustomizedRecordRequest == null)
//            throw new NullParametersException("record");
//        User user = loadUserFromSecurityContext(context);
//        if (user != null) {
//            if (customizedElementServiceFactory.insert(createCustomizedRecordRequest, user.getId())) {
//                return Response.ok().build();
//            } else {
//                log.error("insert in to " + createCustomizedRecordRequest.getElementId() + " error");
//                throw new ApplicationRuntimeException("create record error");
//            }
//
//        } else {
//            log.info("rejected unauth user reading entities action");
//            throw new UnauthorizedUserException("you don't have permit to loading all entities");
//        }
//    }
//
//
//    @RolesAllowed({"ROLE_USER"})
//    @GET
//    @Path("{elementType}/{elementId}")
//    public List getRecords(@Context SecurityContext context, final @PathParam("elementType") CustomizedRecordType type, final @PathParam("elementId") String elementId) throws EntityException {
//        User user = loadUserFromSecurityContext(context);
//        if (user != null) {
//            log.info("reading all record of " + type + " element id=" + elementId + " by " + user.getId());
//            ListCustomizedRecordRequest recordRequest = new ListCustomizedRecordRequest();
//            recordRequest.setType(type);
//            recordRequest.setElementId(elementId);
//            return customizedElementServiceFactory.list(recordRequest, user.getId());
//        } else {
//            log.info("rejected unauth user reading entities action");
//            throw new UnauthorizedUserException("you don't have permit to loading all entities");
//        }
//    }
//
//    @RolesAllowed({"ROLE_USER"})
//    @GET
//    @Path("{elementType}/{elementId}/{recordId}")
//    public Map getRecord(@Context SecurityContext context, final @PathParam("elementType") CustomizedRecordType type, final @PathParam("elementId") String elementId, @PathParam("recordId") int id) {
//        User user = loadUserFromSecurityContext(context);
//        if (user != null) {
//            log.info("reading all record of " + type + " element id=" + elementId + " by " + user.getId());
//            GetCustomizedRecordRequest recordRequest = new GetCustomizedRecordRequest();
//            recordRequest.setType(type);
//            recordRequest.setElementId(elementId);
//            recordRequest.setRecordId(id);
//            return customizedElementServiceFactory.get(recordRequest, user.getId());
//        } else {
//            log.info("rejected unauth user reading entities action");
//            throw new UnauthorizedUserException("you don't have permit to loading all entities");
//        }
//    }
//
//    @RolesAllowed({"ROLE_USER"})
//    @DELETE
//    @Path("{elementType}/{elementId}/{recordId}")
//    public Response deleteRecord(@Context SecurityContext context, final @PathParam("elementType") CustomizedRecordType type, final @PathParam("elementId") String elementId, @PathParam("recordId") int id) {
//        User user = loadUserFromSecurityContext(context);
//        if (user != null) {
//            log.info("reading all record of " + type + " element id=" + elementId + " by " + user.getId());
//            GetCustomizedRecordRequest recordRequest = new GetCustomizedRecordRequest();
//            recordRequest.setType(type);
//            recordRequest.setElementId(elementId);
//            recordRequest.setRecordId(id);
//            boolean result = customizedElementServiceFactory.delete(recordRequest, user.getId());
//            if (result) {
//                return Response.ok().build();
//            } else {
//                log.error("error while delete from" + recordRequest.getElementId() + " id is " + recordRequest.getRecordId());
//                throw new ApplicationRuntimeException("error while delete ");
//            }
//        } else {
//            log.info("rejected unauth user reading entities action");
//            throw new UnauthorizedUserException("you don't have permit to loading all entities");
//        }
//    }
//
//}
