/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.dao;

import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.exception.FileElementDuplicatedException;
import com.oncore.template.model.file.Folder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 2/12/16.
 */
@Repository("folderDao")
public class FolderDaoImpl extends HibernateBaseGenericDaoImpl<Folder,String> implements FolderDao {
    /**
     * 构造方法，根据实例类自动获取实体类类型
     *
     * @param serverHibernateSessionFactory
     */
    @Autowired
    public FolderDaoImpl(SessionFactory serverHibernateSessionFactory) {
        super(serverHibernateSessionFactory);
    }


    @Override
    public List<Folder> getRootFolderByModuleId(String moduleId) {
        Map map = new HashMap<>();
        map.put("module_id",moduleId);
        map.put("depth",0);
        map.put("deleted",false);
        return getListbyParams(map);
    }

    @Override
    public Folder getFolderById(String id) {
        return super.get(id);
    }

    @Override
    public void deleteFolder(String id) {
        Folder folder = this.getFolderById(id);
        if(folder==null){
            throw new ElementNotFoundException("Folder");
        }
        folder.setDeleted(true);
        update(folder);
    }

    @Override
    public Folder updateFolder(String id, Folder folder) {
        Folder folder1 = this.getFolderById(id);
        if(folder1==null){
            throw new ElementNotFoundException("Folder");
        }
        folder1.setDeleted(folder.isDeleted());
        folder1.setName(folder.getName());
        folder1.setDepth(folder.getDepth());
        folder1.setDescription(folder.getDescription());
        folder1.setModule_id(folder.getModule_id());
        folder1.setParentFoldId(folder.getParentFoldId());
        update(folder1);
        return folder1;
    }

    @Override
    public Folder saveFolder(Folder folder) {
        Map query = new HashMap<>();
        if(folder.getParentFoldId() == null){
            folder.setParentFoldId("NULL");
        }
        query.put("parentFoldId", folder.getParentFoldId());
        query.put("name", folder.getName());
        query.put("module_id",folder.getModule_id());
        if( super.getListbyParams(query).size()!=0){
            throw new FileElementDuplicatedException(folder.getName());
        }
        save(folder);
        return folder;
    }

    @Override
    public Folder saveFolderToParentFolder(String parentId, Folder folder) {
        Folder parent = super.checkExist(parentId, " parent folder ");
        folder.setParentFoldId(parent.getId());
        folder.setDepth(parent.getDepth()+1);
        folder.setModule_id(parent.getModule_id());
        Map query = new HashMap<>();
        query.put("parentFoldId", folder.getParentFoldId());
        query.put("name", folder.getName());
        query.put("module_id",folder.getModule_id());
        if( super.getListbyParams(query).size()!=0){
            throw new FileElementDuplicatedException(folder.getName());
        }
        save(folder);
        return folder;
    }

    @Override
    public List<Folder> getFoldersByParentFolder(String parentId) {
        super.checkExist(parentId, " parent folder ");
        Map query = new HashMap<>();
        query.put("parentFoldId", parentId);
        query.put("deleted", false);
        return super.getListbyParams(query);
    }

    @Override
    public void delete(Folder entity) {
        super.delete(entity);
    }

    @Override
    public Folder get(String id) {
        return super.get(id);
    }

    @Override
    public Folder load(String id) {
        return super.load(id);
    }

    @Override
    public List<Folder> loadAll() {
        return super.loadAll();
    }

    @Override
    public void save(Folder entity) {
        super.save(entity);
    }

    @Override
    public void saveOrUpdate(Folder entity) {
        super.saveOrUpdate(entity);
    }

    @Override
    public void update(Folder entity) {
        super.update(entity);
    }

    @Override
    public Folder getbyParam(String param, Object value) {
        return super.getbyParam(param, value);
    }

    @Override
    public List<Folder> getListbyParam(String param, Object value) {
        return super.getListbyParam(param, value);
    }

    @Override
    public List<Folder> getListbyField(String[] fields) {
        return super.getListbyField(fields);
    }

    @Override
    public List<Folder> getListbyParams(Map<String, Object> map) {
        return super.getListbyParams(map);
    }

    @Override
    public List<Folder> getListbyFieldAndParams(String[] fields, Map<String, Object> map) {
        return super.getListbyFieldAndParams(fields, map);
    }

    @Override
    public Folder checkExist(String id, String msg) {
        return super.checkExist(id, msg);
    }

}
