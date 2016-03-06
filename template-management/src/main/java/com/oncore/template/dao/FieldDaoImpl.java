package com.oncore.template.dao;

import com.oncore.template.model.Field;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/15/16.
 */
@Repository("fieldDao")
public class FieldDaoImpl extends HibernateBaseGenericDaoImpl<Field,String > implements FieldDao {

    private static Log logger = LogFactory.getLog(HibernateBaseGenericDaoImpl.class);

    /**
     * 构造方法，根据实例类自动获取实体类类型
     *
     * @param sessionFactory
     */
    @Autowired
    public FieldDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Field> findFieldByEntityId(String entity_id) {
        Map map = new HashMap<>();
        map.put("entity_id",entity_id);
        map.put("deleted",false);
        return  super.getListbyParams(map);
    }

    /**
     * set field.deleted to true of the fields which entity_id is specified
     * */
    @Override
    public void deleteFieldByEntityId(String entity_id) {
        Session session = getSessionFactory().getCurrentSession();
        Query query =session.createQuery("update Field field set field.deleted=true where field.entity=:entity_id");
        query.setString("entity_id",entity_id);
        int rowCount = query.executeUpdate();
        logger.info("deleted "+rowCount+" fields of entity("+entity_id+")");
    }

    /***/
    @Override
    public void delete(Field entity) {
        super.delete(entity);
    }

    @Override
    public Field get(String id) {
        return super.get(id);
    }

    @Override
    public Field load(String id) {
        return super.load(id);
    }

    @Override
    public List<Field> loadAll() {
        return super.loadAll();
    }

    @Override
    public void save(Field entity) {
        super.save(entity);
    }

    @Override
    public void saveOrUpdate(Field entity) {
        super.saveOrUpdate(entity);
    }

    @Override
    public void update(Field entity) {
        super.update(entity);
    }

    @Override
    public Field getbyParam(String param, Object value) {
        return super.getbyParam(param, value);
    }

    @Override
    public List<Field> getListbyParam(String param, Object value) {
        return super.getListbyParam(param, value);
    }

    @Override
    public List<Field> getListbyField(String[] fields) {
        return super.getListbyField(fields);
    }

    @Override
    public List<Field> getListbyParams(Map<String, Object> map) {
        return super.getListbyParams(map);
    }

    @Override
    public List<Field> getListbyFieldAndParams(String[] fields, Map<String, Object> map) {
        return super.getListbyFieldAndParams(fields, map);
    }
}
