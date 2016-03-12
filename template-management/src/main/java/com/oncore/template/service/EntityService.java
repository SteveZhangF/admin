package com.oncore.template.service;


import com.oncore.template.model.Entity;
import com.oncore.template.transfd.entities.CreateEntityRequest;
import com.oncore.template.transfd.entities.UpdateEntityRequest;

import java.util.List;

/**
 * Created by steve on 1/14/16.
 */
public interface EntityService extends IBaseGenericService<Entity,String>{
    Entity createEntity(Entity entity);
    Entity createEntityFromRequest(String module_id, CreateEntityRequest entityRequest);
    Entity updateEntity(Entity entity);
    Entity updateEntityFromRequest(UpdateEntityRequest entityRequest);
    Entity getEntity(String id);
    void deleteEntity(String id);
    String getEntityTableName(String id);
    List<Entity> listEntities();
    Entity getEntityDesc(String id);

    List<Entity> getEntitiesOfModule(String moduleId);

    /**
     * 判断是否有重复的fields
     * */
    void checkFields(Entity entity) ;

    List<Entity> getEntityUnderModule(String moduleId);
}
