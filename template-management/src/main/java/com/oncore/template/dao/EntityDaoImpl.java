package com.oncore.template.dao;

import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.model.Entity;
import com.oncore.template.model.Field;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/15/16.
 */
@Repository("entityDao")
public class EntityDaoImpl extends HibernateBaseGenericDaoImpl<Entity, String> implements EntityDao {
    /**
     * 构造方法，根据实例类自动获取实体类类型
     *
     * @param sessionFactory
     */
    @Autowired
    public EntityDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Entity get(String id) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select  entity from Entity as entity where entity.deleted=false and entity.id=:id ");
        query.setString("id", id);
        Entity entity = (Entity) query.uniqueResult();
        Query query1 = session.createQuery("select field from Field as field where field.entity=:id and field.deleted=false");
        query1.setString("id", id);
        List<Field> fields = query1.list();
        if (entity != null) {
            entity.setFields(fields);
        }
        return entity;
    }

    @Override
    public void deleteEntity(String id) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("update Entity entity set entity.deleted=true where entity.id=:id");
        query.setString("id", id);
        int i = query.executeUpdate();
        if (i == 0) {
            throw new ElementNotFoundException("entity");
        }
    }

    @Override
    public String getEntityTableName(String id) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select entity.tableName from Entity entity where entity.id=:id");
        query.setString("id", id);
        String tableName = (String) query.uniqueResult();
        return tableName;
    }

    @Override
    public List<Entity> getEntitiesByModuleId(String moduleId) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select  entity from Entity as entity where entity.deleted=false and entity.module_id=:module_id ");
        query.setString("module_id", moduleId);
        List<Entity> entityList = query.list();

        for (Entity e : entityList) {
            Query query1 = session.createQuery("select field from Field as field where field.entity=:id and field.deleted=false");
            query1.setString("id", e.getId());
            List<Field> fields = query1.list();
            e.setFields(fields);
        }
        return entityList;
    }

    @Override
    public void delete(Entity entity) {
        super.delete(entity);
    }

    @Override
    public Entity load(String id) {
        return super.load(id);
    }

    @Override
    public List<Entity> loadAll() {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select  entity from Entity as entity where entity.deleted=false  ");
        List<Entity> entityList = query.list();

        for (Entity e : entityList) {
            Query query1 = session.createQuery("select field from Field as field where field.entity=:id and field.deleted=false");
            query1.setString("id", e.getId());
            List<Field> fields = query1.list();
            e.setFields(fields);
        }
        return entityList;
    }

    @Override
    public void save(Entity entity) {
        super.save(entity);
    }

    @Override
    public void saveOrUpdate(Entity entity) {
        super.saveOrUpdate(entity);
    }

    @Override
    public void update(Entity entity) {
        super.update(entity);
    }

    @Override
    public Entity getbyParam(String param, Object value) {
        return super.getbyParam(param, value);
    }

    @Override
    public List<Entity> getListbyParam(String param, Object value) {
        return super.getListbyParam(param, value);
    }

    @Override
    public List<Entity> getListbyField(String[] fields) {
        return super.getListbyField(fields);
    }

    @Override
    public List<Entity> getListbyParams(Map<String, Object> map) {
        return super.getListbyParams(map);
    }

    @Override
    public List<Entity> getListbyFieldAndParams(String[] fields, Map<String, Object> map) {
        return super.getListbyFieldAndParams(fields, map);
    }
}
