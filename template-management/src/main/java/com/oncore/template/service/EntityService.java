package com.oncore.template.service;


import com.oncore.template.model.Entity;
import com.oncore.template.transfd.entities.CreateEntityRequest;
import com.oncore.template.transfd.entities.EntityResponse;
import com.oncore.template.transfd.entities.UpdateEntityRequest;

import java.util.List;

/**
 * Created by steve on 1/14/16.
 */
public interface EntityService extends IBaseGenericService<Entity,String>{
    EntityResponse createEntity(Entity entity);
    EntityResponse createEntityFromRequest(String module_id, CreateEntityRequest entityRequest);
    EntityResponse updateEntity(Entity entity);
    EntityResponse updateEntityFromRequest(UpdateEntityRequest entityRequest);
    EntityResponse getEntity(String id);
    void deleteEntity(String id);
    String getEntityTableName(String id);
    List<EntityResponse> listEntities();
    EntityResponse getEntityDesc(String id);

    List<EntityResponse> getEntitiesOfModule(String moduleId);
    List<Entity> getUserEntitiesOfModule(String moduleId);
    /**
     * 判断是否有重复的fields
     * */
    void checkFields(Entity entity) ;

    List<EntityResponse> getEntityUnderModule(String moduleId);
}
