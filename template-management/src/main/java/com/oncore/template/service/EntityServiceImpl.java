package com.oncore.template.service;

import com.oncore.data.TableBuilder;
import com.oncore.template.dao.EntityDao;
import com.oncore.template.dao.FieldDao;
import com.oncore.template.dao.ModuleDao;
import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.exception.EntityFieldDuplicatedException;
import com.oncore.template.model.Entity;
import com.oncore.template.model.Field;
import com.oncore.template.transfd.entities.CreateEntityRequest;
import com.oncore.template.transfd.entities.FieldRequest;
import com.oncore.template.transfd.entities.UpdateEntityRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.Validator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/14/16.
 */
@Service
@Transactional
public class EntityServiceImpl extends BaseGenericServiceImpl<Entity, String> implements EntityService {
    @Autowired
    EntityDao entityDao;
    @Autowired
    FieldDao fieldDao;
    @Autowired
    ModuleDao moduleDao;
    private static Log logger = LogFactory.getLog(EntityServiceImpl.class);

    @Autowired
    public EntityServiceImpl(Validator validator) {
        super(validator);
    }
    /**
     * create one entity and generate related files and table
     */
    @Autowired
    TableBuilder tableBuilder;
    @Override
    public Entity createEntity(Entity entity) {
        checkFields(entity);
        Long now = new Date().getTime();
        String tableName = entity.getName() + "_" + now;
        entity.setTableName(tableName);
        entity.setHbmPath(entity.getName() + "/" + entity.getTableName());
        entity.setDeleted(false);
        entityDao.save(entity);
        for (Field field : entity.getFields()) {
            field.setEntity(entity);
            field.setDeleted(false);
            fieldDao.save(field);
        }
        //TODO
       // iTableCreator.createTable(entity);
        try {
            tableBuilder.createMappingFile(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Entity createEntityFromRequest(String module_id,CreateEntityRequest entityRequest) {
        validate(entityRequest);
        Entity entity = new Entity();
        entity.setName(entityRequest.getName());
        entity.setDescription(entityRequest.getDescription());
        entity.setModule_id(module_id);
        for (FieldRequest fieldRequest : entityRequest.getFields()) {
            Field field = new Field();
            field.setName(fieldRequest.getName());
            field.setDescription(fieldRequest.getDescription());
            field.setLength(fieldRequest.getLength());
            field.setFieldType(fieldRequest.getFieldType());
            field.setIfNull(fieldRequest.isIfNull());
            entity.addField(field);
        }
        return createEntity(entity);
    }

    /**
     * update one entity then regenerate or update the related files and table
     */
    @Override
    public Entity updateEntity(Entity entity) throws ElementNotFoundException {
        checkFields(entity);
        if (getEntity(entity.getId()) == null)
            throw new ElementNotFoundException("entity");
        fieldDao.deleteFieldByEntityId(entity.getId());
        for (Field field : entity.getFields()) {
            Field old = null;
            if (field.getId() != null) {
                old = fieldDao.get(field.getId());
            }
            if (null != field.getId() && null != old) {
                old.setFieldType(field.getFieldType());
                old.setIfNull(field.isIfNull());
                old.setEntity(field.getEntity());
                old.setCreateTime(field.getCreateTime());
                old.setUpdateTime(field.getUpdateTime());
                old.setName(field.getName());
                old.setDeleted(false);
                old.setLength(field.getLength());
                old.setDescription(field.getDescription());
                fieldDao.update(old);
            } else {
                fieldDao.save(field);
            }
        }
        Entity oldEntity = getEntity(entity.getId());
        if (oldEntity != null) {
            oldEntity.setFields(entity.getFields());
            oldEntity.setDescription(entity.getDescription());
            oldEntity.setDeleted(entity.isDeleted());
            oldEntity.setCreateTime(entity.getCreateTime());
            oldEntity.setUpdateTime(entity.getUpdateTime());
            oldEntity.setName(entity.getName());
            oldEntity.setId(entity.getId());
            entityDao.update(oldEntity);
        }

        //TODO iTableCreator.updateTable(oldEntity);
        return oldEntity;
    }

    @Override
    public Entity updateEntityFromRequest(UpdateEntityRequest entityRequest) {
        validate(entityRequest);
        Entity entity = new Entity();
        entity.setId(entityRequest.getId());
        entity.setName(entityRequest.getName());
        entity.setDescription(entityRequest.getDescription());
        for (FieldRequest fieldRequest : entityRequest.getFields()) {
            Field field = new Field();
            field.setId(fieldRequest.getId());
            field.setName(fieldRequest.getName());
            field.setDescription(fieldRequest.getDescription());
            field.setLength(fieldRequest.getLength());
            field.setFieldType(fieldRequest.getFieldType());
            field.setIfNull(fieldRequest.isIfNull());
            entity.addField(field);
        }
        return updateEntity(entity);
    }

//    /**
//     * create table files of entity
//     * 1.create hbmapping file of entity
//     * 2.create table
//     * 3.create groovy dao script file
//     */
//    @Override
//    public void createTableFiles(Entity entity) {
//        try {
//            hbmMaker.createTemplateFile(entity);
//            tableCreator.createTable(entity);
//            entityDaoMaker.createTemplateFile(entity);
//        } catch (IOException e) {
//            logger.error(e);
//            throw new ApplicationRuntimeException("error while creating table " + entity.getId() + " files");
//        } catch (TemplateException e) {
//            logger.error(e);
//            throw new ApplicationRuntimeException("error while creating table  " + entity.getId() + "files");
//        } catch (SQLException e) {
//            logger.error(e);
//            throw new ApplicationRuntimeException("error while creating table  " + entity.getId() + "table");
//
//        }
//    }

    @Override
    public List<Entity> getEntitiesOfModule(String moduleId) {
        return entityDao.getEntitiesByModuleId(moduleId);
    }

    /**
     * get entity details
     */
    @Override
    public Entity getEntity(String id) throws ElementNotFoundException {
        Entity entity = entityDao.get(id);
        if (entity == null) {
            logger.error("can't find entity id=" + id);
            throw new ElementNotFoundException("entity");
        }
        logger.info("found entity id=" + entity.getId() + " name=" + entity.getName());
        return entity;
    }


    @Override
    public List<Entity> getEntityUnderModule(String moduleId) {
        moduleDao.checkExist(moduleId," module is not exist");
        Map query = new HashMap<>();
        query.put("deleted",false);
        query.put("moduleId",moduleId);
        return entityDao.getListbyParams(query);
    }

    /**
     * get table name of one entity
     */
    @Override
    public String getEntityTableName(String id) {
        return entityDao.getEntityTableName(id);
    }

    /**
     * delete one entity
     */
    @Override
    public void deleteEntity(String id) {
        entityDao.deleteEntity(id);
    }

    /**
     * list all entities without fields
     */
    @Override
    public List<Entity> listEntities() {
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", false);
        String[] fields = new String[]{"id", "name", "createTime", "updateTime", "description", "hbmPath", "tableName"};
        List entities = entityDao.getListbyFieldAndParams(fields, map);
        return entities;
    }

    /**
     * get an entity without fields
     */
    @Override
    public Entity getEntityDesc(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", false);
        map.put("id", id);
        String[] fields = new String[]{"id", "name", "createTime", "updateTime", "description", "hbmPath", "tableName"};
        List<Entity> entities = entityDao.getListbyFieldAndParams(fields, map);
        if (entities.size() == 1) {
            return entities.get(0);
        } else
            return null;
    }

    /**
     * check whether has duplicated fields name
     */
    @Override
    public void checkFields(Entity entity) throws EntityFieldDuplicatedException {
        int i = 0, j = 0;
        for (Field field : entity.getFields()) {
            System.out.println(i + "/" + j);
            for (Field field1 : entity.getFields()) {
                if (field.getName().equals(field1.getName()) && i != j) {
                    throw new EntityFieldDuplicatedException(field.getName());
                }
                j++;
            }
            j = 0;
            i++;
        }

    }
}
