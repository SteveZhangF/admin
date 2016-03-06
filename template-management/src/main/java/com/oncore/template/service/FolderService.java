/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.service;

import com.oncore.template.model.file.Folder;
import com.oncore.template.transfd.file_element.folder.CreateFolderToParentFolderRequest;
import com.oncore.template.transfd.file_element.folder.CreateRootFolderRequest;
import com.oncore.template.transfd.file_element.folder.FolderResponse;
import com.oncore.template.transfd.file_element.folder.UpdateFolderRequest;

import java.util.List;

/**
 * Created by steve on 2/12/16.
 */
public interface FolderService extends IBaseGenericService<Folder,String>{



    List<Folder> getRootFoldersUnderModule(String moduleId);
    FolderResponse createRootFolderUnderModule(CreateRootFolderRequest createRootFolderRequest);
    List<FolderResponse> getChildFolder(String id);
    FolderResponse createFolderToParent(CreateFolderToParentFolderRequest createFolderToParentFolderRequest);
    List<FolderResponse> getRootFolderUnderModule(String moduleId);
    FolderResponse updateFolderFromRequest(String folderId, UpdateFolderRequest request);
    void deleteFolder(String id);

}
