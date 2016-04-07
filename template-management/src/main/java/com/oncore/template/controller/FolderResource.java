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
import com.oncore.template.model.file.Folder;
import com.oncore.template.model.file.Report;
import com.oncore.template.service.FolderService;
import com.oncore.template.service.ReportService;
import com.oncore.template.transfd.file_element.folder.CreateFolderToParentFolderRequest;
import com.oncore.template.transfd.file_element.folder.FolderResponse;
import com.oncore.template.transfd.file_element.folder.UpdateFolderRequest;
import com.oncore.template.transfd.report.CreateReportRequest;
import com.oncore.template.transfd.report.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 2/12/16.
 */
@RestController
public class FolderResource extends BaseController {
    @Autowired
    FolderService folderService;
    /**
     * get folder based on id
     * */
    @RequestMapping(value = "/admin/folders/{id}", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public FolderResponse getFolder(@PathVariable("id") String id) {
        super.checkUserContext();
        Folder folder = folderService.get(id);
        if (folder == null) {
            throw new ElementNotFoundException("folder");
        }
        FolderResponse response = new FolderResponse(folder);
        return response;
    }

    /**
     * create sub folder under parent
     * */
    @RequestMapping(value = "/admin/folders/{parent_id}/sub_folder/", method = RequestMethod.POST, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public FolderResponse createSubFolderToParent(@PathVariable("parent_id") String parent_id, @RequestBody CreateFolderToParentFolderRequest subFolderRequest) {
        this.checkUserContext();
        FolderResponse response = folderService.createFolderToParent(parent_id, subFolderRequest);
        return response;
    }

    /**
     * get child folders under parent folder
     * */
    @RequestMapping(value = "/admin/folders/{parent_id}/sub_folder/", method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_VALUE)

    public List<FolderResponse> getChildFoldersUnderParentFolder(@PathVariable("parent_id") String parentId) {
        this.checkUserContext();
        List<FolderResponse> list = folderService.getChildFolder(parentId);
        return list;
    }

    @Autowired
    ReportService reportService;

    /**
     * get reports under folder
     * */
    @RequestMapping(value = "/admin/folders/{folderId}/reports/", method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_VALUE)

    public List<ReportResponse> getReportsUnderFolder(@PathVariable("folderId") String folderId) {
        this.checkUserContext();
        List<ReportResponse> reportResponses = new ArrayList<>();
        List<Report> reportList = reportService.listReportsUnderFolder(folderId);
        for (Report report : reportList) {
            reportResponses.add(new ReportResponse(report));
        }
        return reportResponses;
    }

    /**
     * create report under folder
     * */
    @RequestMapping(value = "/admin/folders/{folderId}/reports/", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<ReportResponse> createReportsUnderFolder(@PathVariable("folderId") String folderId
            , @RequestBody CreateReportRequest reportRequest) {
        this.checkUserContext();
        return new ResponseEntity<>(reportService.createReportFromRequest(folderId, reportRequest), HttpStatus.OK);
    }


    /**
     * update folder
     * */
    @RequestMapping(value = "/admin/folders/{id}/", method = RequestMethod.PUT
            , produces = MediaType.APPLICATION_JSON_VALUE)

    public FolderResponse updateFolder(@PathVariable("id") String id, @RequestBody UpdateFolderRequest request) {
        this.checkUserContext();
        return folderService.updateFolderFromRequest(id, request);
    }

    /**
     * delete folder
     * */
    @RequestMapping(value = "/admin/folders/{id}/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteFolder(@PathVariable("id") String id) {
        folderService.deleteFolder(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
