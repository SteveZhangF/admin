/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.dao;


import com.oncore.template.model.file.Folder;

import java.util.List;

/**
 * Created by steve on 2/12/16.
 */
public interface FolderDao  extends IBaseGenericDao<Folder,String> {

    List<Folder> getRootFolderByModuleId(String moduleId);
    Folder getFolderById(String id);
    void deleteFolder(String id);
    Folder updateFolder(String id, Folder folder);
    Folder saveFolder(Folder folder);
    Folder saveFolderToParentFolder(String parentId, Folder folder);
    List<Folder> getFoldersByParentFolder(String parentId);
}
