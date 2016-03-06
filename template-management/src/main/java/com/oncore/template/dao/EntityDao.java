package com.oncore.template.dao;



import com.oncore.template.model.Entity;

import java.util.List;

/**
 * Created by steve on 1/15/16.
 */
public interface EntityDao extends IBaseGenericDao<Entity,String> {
    void deleteEntity(String id);
    String getEntityTableName(String id);
    List<Entity> getEntitiesByModuleId(String moduleId);
}
