package com.oncore.template.service;

import com.oncore.data.messagequeue.producer.ProducerService;
import com.oncore.template.dao.EntityDao;
import com.oncore.template.dao.FieldDao;
import com.oncore.template.dao.ModuleDao;
import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.exception.EntityFieldDuplicatedException;
import com.oncore.template.model.Entity;
import com.oncore.template.model.Field;
import com.oncore.template.transfd.entities.CreateEntityRequest;
import com.oncore.template.transfd.entities.EntityResponse;
import com.oncore.template.transfd.entities.FieldRequest;
import com.oncore.template.transfd.entities.UpdateEntityRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.*;

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
    @Qualifier("tableBuilderMessageSender")
    ProducerService tableBuilder;

    @Override
    public EntityResponse createEntity(Entity entity) {
        checkFields(entity);
        Long now = new Date().getTime();
        String tableName = "entity_" + entity.getName().replace(" ", "_") + "_" + now;
        entity.setTableName(tableName);
        entity.setHbmPath(entity.getName().replace(" ", "_") + "/" + entity.getTableName());
        entity.setDeleted(false);
        entity.setUnique_(entity.isUnique_());
        entityDao.save(entity);
        for (Field field : entity.getFields()) {
            field.setEntity(entity);
            field.setDeleted(false);
            field.setTableName(entity.getTableName());
            fieldDao.save(field);
        }
        //TODO
        // iTableCreator.createTable(entity);
        try {
            tableBuilder.sendMessage(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EntityResponse(entity);
    }

    @Override
    public EntityResponse createEntityFromRequest(String module_id, CreateEntityRequest entityRequest) {
        validate(entityRequest);
        Entity entity = new Entity();
        entity.setName(entityRequest.getName());
        entity.setUnique_(entityRequest.isUnique());
        entity.setPriority(entityRequest.getPriority());
        entity.setDescription(entityRequest.getDescription());
        entity.setModule_id(module_id);
        for (FieldRequest fieldRequest : entityRequest.getFields()) {
            Field field = new Field();
            field.setDescription(fieldRequest.getDescription());
            field.setName(fieldRequest.getName().replace(" ", "_"));
            field.setLength(fieldRequest.getLength() == 0 ? 256 : fieldRequest.getLength());

            field.setFieldType(fieldRequest.getFieldType() == null ? "string" : fieldRequest.getFieldType());
            field.setIfNull(fieldRequest.isIfNull());
            entity.addField(field);
        }
        return createEntity(entity);
    }

    /**
     * update one entity then regenerate or update the related files and table
     */
    @Override
    public EntityResponse updateEntity(Entity entity) throws ElementNotFoundException {
        checkFields(entity);

        Entity oldEntity = entityDao.get(entity.getId());
        if (oldEntity == null)
            throw new ElementNotFoundException("entity");
        List<Field> oldFields = oldEntity.getFields();

        for (Field field : entity.getFields()) {
            if ((field.getId() != null) && !(fieldDao.get(field.getId()) == null)) {

                Iterator<Field> iter = oldFields.iterator();
                while (iter.hasNext()) {
                    Field oldField = iter.next();
                    if (oldField.getId().equals(field.getId())) {
                        oldField.setFieldType(field.getFieldType());
                        oldField.setIfNull(field.isIfNull());
                        oldField.setEntity(field.getEntity());
                        oldField.setName(field.getName());
                        oldField.setDeleted(field.isDeleted());
                        oldField.setLength(field.getLength());
                        oldField.setDescription(field.getDescription());
                        if (oldField.isDeleted()) {
                            iter.remove();
                        }
                    }
                }

            } else {
                logger.info("adding fields");
                field.setTableName(oldEntity.getTableName());
                oldEntity.addField(field);
                fieldDao.save(field);
            }
        }
        oldEntity.setUnique_(entity.isUnique_());
        oldEntity.setDescription(entity.getDescription());
        oldEntity.setDeleted(entity.isDeleted());
        oldEntity.setName(entity.getName());
        entityDao.update(oldEntity);
        try {
            tableBuilder.sendMessage(oldEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EntityResponse(oldEntity);
    }

    @Override
    public EntityResponse updateEntityFromRequest(UpdateEntityRequest entityRequest) {
        validate(entityRequest);
        Entity entity = new Entity();
        entity.setId(entityRequest.getId());
        entity.setName(entityRequest.getName());
        entity.setUnique_(entityRequest.isUnique());
        entity.setPriority(entityRequest.getPriority());
        entity.setDescription(entityRequest.getDescription());
        for (FieldRequest fieldRequest : entityRequest.getFields()) {
            Field field = new Field();
            field.setId(fieldRequest.getId());
            field.setName(fieldRequest.getName());
            field.setDescription(fieldRequest.getDescription());

            field.setName(fieldRequest.getName().replace(" ", "_"));
            field.setLength(fieldRequest.getLength() == 0 ? 256 : fieldRequest.getLength());
            field.setFieldType(fieldRequest.getFieldType() == null ? "string" : fieldRequest.getFieldType());

            field.setIfNull(fieldRequest.isIfNull());
            field.setDeleted(fieldRequest.isDeleted());
            entity.addField(field);
        }
        return updateEntity(entity);
    }

    @Override
    public List<EntityResponse> getEntitiesOfModule(String moduleId) {
        List<Entity> list = entityDao.getEntitiesByModuleId(moduleId);
        List<EntityResponse> listRe = new ArrayList<>();
        for (Entity entity : list) {
            listRe.add(new EntityResponse(entity));
        }
        return listRe;
    }

    /**
     * get entity details
     */
    @Override
    public EntityResponse getEntity(String id) throws ElementNotFoundException {
        Entity entity = entityDao.get(id);
        if (entity == null) {
            throw new ElementNotFoundException("entity");
        }
        return new EntityResponse(entity);
    }


    @Override
    public List<EntityResponse> getEntityUnderModule(String moduleId) {
        moduleDao.checkExist(moduleId, " module is not exist");
        Map query = new HashMap<>();
        query.put("deleted", false);
        query.put("module_id", moduleId);
        List<Entity> list = entityDao.getListbyParams(query);
        List<EntityResponse> listR = new ArrayList<>();
        for (Entity entity : list) {
            listR.add(new EntityResponse(entity));
        }
        return listR;
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
    public List<EntityResponse> listEntities() {
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", false);
        String[] fields = new String[]{"id", "name", "createTime", "updateTime", "description", "hbmPath", "tableName","unique_","priority"};
        List<Entity> entities = entityDao.getListbyFieldAndParams(fields, map);
        List<EntityResponse> listR = new ArrayList<>();
        for (Entity entity : entities) {
            listR.add(new EntityResponse(entity));
        }
        return listR;
    }

    @Override
    public List<Entity> getUserEntitiesOfModule(String moduleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", false);
        map.put("module_id", moduleId);
        String[] fields = new String[]{"id", "name","tableName","unique_","priority"};
        List<Entity> entities = entityDao.getListbyFieldAndParams(fields, map);

        return entities;
    }

    @Override
    public List<Entity> loadAll() {
        return entityDao.loadAll();
    }

    /**
     * get an entity without fields
     */
    @Override
    public EntityResponse getEntityDesc(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", false);
        map.put("id", id);
        String[] fields = new String[]{"id", "name", "createTime", "updateTime", "description", "hbmPath", "tableName","unique_","priority"};
        List<Entity> entities = entityDao.getListbyFieldAndParams(fields, map);
        if (entities.size() == 1) {
            return new EntityResponse(entities.get(0));
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
