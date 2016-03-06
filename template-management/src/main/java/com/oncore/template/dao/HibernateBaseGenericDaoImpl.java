/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.dao;

/**
 * Created by steve on 10/12/15.
 */

import com.oncore.template.model.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @param <T>  实体类
 * @param <PK> 主键类，必须实现Serializable接口
 * @author zj
 * @version V1.0
 * @ClassName：HibernateBaseGenericDAOImpl
 * @Description：TODO(HibernateBaseGenericDAOImpl DAO层泛型基类，实现了基本的DAO功能 利用了Spring的HibernateDaoSupport功能)
 * @date：Jul 9, 2012 1:41:37 PM
 */
@Repository("hibernateBaseGenericDao")
@SuppressWarnings("all")
public class HibernateBaseGenericDaoImpl<T extends Element, PK extends Serializable> extends HibernateDaoSupport implements IBaseGenericDao<T, PK> {

    private static Log logger = LogFactory.getLog(HibernateBaseGenericDaoImpl.class);

    protected Class<T> entityClass;
//    @Autowired
//    SessionFactory sessionFactory;

    /**
     * 构造方法，根据实例类自动获取实体类类型
     */
    @Autowired
    public HibernateBaseGenericDaoImpl(SessionFactory serverHibernateSessionFactory) {
        this.setSessionFactory(serverHibernateSessionFactory);
        this.entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }

    /*
     * (non-Javadoc)
     * @see com.integration.framework.dao.IBaseGenericDao#delete(java.lang.Object)
     */
    public void delete(T entity) {
        try {
            getHibernateTemplate().delete(entity);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.integration.framework.dao.IBaseGenericDao#get(java.io.Serializable)
     */
    public T get(PK id) {
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    /*
     * (non-Javadoc)
     * @see com.integration.framework.dao.IBaseGenericDao#load(java.io.Serializable)
     */
    public T load(PK id) {
        return (T) getHibernateTemplate().load(entityClass, id);
    }

    /*
     * (non-Javadoc)
     * @see com.integration.framework.dao.IBaseGenericDao#loadAll()
     */
    public List<T> loadAll() {
        return getHibernateTemplate().loadAll(entityClass);
    }

    /*
     * (non-Javadoc)
     * @see com.integration.framework.dao.IBaseGenericDao#save(java.lang.Object)
     */
    public void save(T entity) {
        try {
            logger.info("saving " + entity.getClass().getSimpleName() + " " + entity.getName());
            entity.setCreateTime(new Timestamp(new Date().getTime()));
            entity.setUpdateTime(new Timestamp(new Date().getTime()));
            getHibernateTemplate().save(entity);
            logger.info("save " + entity.getClass().getSimpleName() + " " + entity.getName() + " success");

        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.integration.framework.dao.IBaseGenericDao#saveOrUpdate(java.lang.Object)
     */
    public void saveOrUpdate(T entity) {
        try {
            getHibernateTemplate().saveOrUpdate(entity);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.integration.framework.dao.IBaseGenericDao#update(java.lang.Object)
     */
    public void update(T entity) {
        try {
            logger.info("updating " + entity.getClass().getSimpleName() + " id=" + entity.getId());
            entity.setUpdateTime(new Timestamp(new Date().getTime()));
            getHibernateTemplate().update(entity);
            logger.info("updated " + entity.getClass().getSimpleName() + " id=" + entity.getId());

        } catch (DataAccessException e) {
            logger.info("updated failed + " + entity.getClass().getSimpleName() + " id=" + entity.getId());
            logger.error(e.getMessage(), e);
        }
    }

    public T getbyParam(String param, Object value) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        Object yourObject = criteria.add(Restrictions.eq(param, value))
                .uniqueResult();
        return (T) yourObject;
    }

    @Override
    public List<T> getListbyParam(String param, Object value) {

        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        List<T> list = criteria.add(Restrictions.eq(param, value))
                .list();
        return list;
    }

    @Override
    public List<T> getListbyField(String[] fields) {
        Criteria cr = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        ProjectionList projectionList = Projections.projectionList();
        for (String field : fields) {
            projectionList.add(Projections.property(field), field);
        }
        cr.setProjection(projectionList);
        cr.setResultTransformer(Transformers.aliasToBean(entityClass));
        List<T> list = cr.list();
        return list;
    }

    @Override
    public List<T> getListbyParams(Map<String, Object> map) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        for (String key : map.keySet()) {
            criteria.add(Restrictions.eq(key, map.get(key)));
        }
        List<T> list = criteria.list();
        return list;
    }

    @Override
    public List<T> getListbyFieldAndParams(String[] fields, Map<String, Object> map) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        for (String key : map.keySet()) {
            criteria.add(Restrictions.eq(key, map.get(key)));
        }
        ProjectionList projectionList = Projections.projectionList();
        for (String field : fields) {
            projectionList.add(Projections.property(field), field);
        }
        criteria.setProjection(projectionList);
        criteria.setResultTransformer(Transformers.aliasToBean(entityClass));
        List<T> list = criteria.list();
        return list;
    }


    @Override
    public T checkExist(PK id, String msg) {
        T obj = this.get(id);
        if(null == obj){
//            throw new ElementNotFoundException(msg);
        }
        return obj;
    }
}

