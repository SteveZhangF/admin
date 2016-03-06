/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.service;

import com.oncore.template.dao.FolderDao;
import com.oncore.template.dao.ModuleDao;
import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.model.file.Folder;
import com.oncore.template.transfd.file_element.folder.CreateFolderToParentFolderRequest;
import com.oncore.template.transfd.file_element.folder.CreateRootFolderRequest;
import com.oncore.template.transfd.file_element.folder.FolderResponse;
import com.oncore.template.transfd.file_element.folder.UpdateFolderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 2/12/16.
 */
@Service
@Transactional
public class FolderServiceImpl extends BaseGenericServiceImpl<Folder, String> implements FolderService {

    @Autowired
    FolderDao folderDao;
    @Autowired
    ModuleDao moduleDao;

    @Autowired
    public FolderServiceImpl(Validator validator) {
        super(validator);
    }

    @Override
    public FolderResponse createRootFolderUnderModule(CreateRootFolderRequest createRootFolderRequest) {
        validate(createRootFolderRequest);
        moduleDao.checkExist(createRootFolderRequest.getModuleId(), " module ");
        Folder folder = new Folder();
        folder.setDepth(0);
        folder.setName(createRootFolderRequest.getName());
        folder.setDescription(createRootFolderRequest.getDescription());
        folder.setModule_id(createRootFolderRequest.getModuleId());
        folderDao.saveFolder(folder);
        FolderResponse response = new FolderResponse(folder);
        return response;
    }

    @Override
    public List<FolderResponse> getChildFolder(String id) {
        List<Folder> list = folderDao.getFoldersByParentFolder(id);
        List<FolderResponse> listResponse = new ArrayList<>();
        for (Folder folder : list) {
            listResponse.add(new FolderResponse(folder));
        }
        return listResponse;
    }

    @Override
    public FolderResponse createFolderToParent(CreateFolderToParentFolderRequest createFolderToParentFolderRequest) {
        validate(createFolderToParentFolderRequest);
        Folder folder = new Folder();
        folder.setDescription(createFolderToParentFolderRequest.getDescription());
        folder.setName(createFolderToParentFolderRequest.getName());
        folder.setDeleted(false);
        folderDao.saveFolderToParentFolder(createFolderToParentFolderRequest.getParentFolderId(), folder);
        return new FolderResponse(folder);
    }
    @Override
    public List<Folder> getRootFoldersUnderModule(String moduleId) {
        moduleDao.checkExist(moduleId,"no such module");
        List<Folder> list = folderDao.getRootFolderByModuleId(moduleId);
        return list;
    }

    @Override
    public List<FolderResponse> getRootFolderUnderModule(String moduleId) {
        moduleDao.checkExist(moduleId,"no such module");
        List<Folder> list = folderDao.getRootFolderByModuleId(moduleId);
        List<FolderResponse> listResponse = new ArrayList<>();
        for(Folder folder: list){
            FolderResponse response = new FolderResponse(folder);
            listResponse.add(response);
        }
        return listResponse;
    }

    @Override
    public void deleteFolder(String id) {
        folderDao.deleteFolder(id);
    }


    @Override
    public FolderResponse updateFolderFromRequest(String folderId, UpdateFolderRequest request) {
        Folder folder = folderDao .getFolderById(folderId);
        if(folder == null){
            throw new ElementNotFoundException("folder");
        }
        folder.setName(request.getName());
        folder.setDescription(request.getDescription());
        folderDao.updateFolder(folderId,folder);
        return new FolderResponse(folder);
    }

    @Override
    protected void validate(Object request) {
        super.validate(request);
    }

    @Override
    public void delete(Folder entity) {
        folderDao.delete(entity);
    }

    @Override
    public Folder get(String id) {
        return folderDao.get(id);
    }

    @Override
    public Folder load(String id) {
        return folderDao.load(id);
    }

    @Override
    public List<Folder> loadAll() {
        return folderDao.loadAll();
    }

    @Override
    public void save(Folder entity) {
        folderDao.save(entity);
    }

    @Override
    public void saveOrUpdate(Folder entity) {
        folderDao.saveOrUpdate(entity);
    }

    @Override
    public void update(Folder entity) {
        folderDao.update(entity);
    }


}

