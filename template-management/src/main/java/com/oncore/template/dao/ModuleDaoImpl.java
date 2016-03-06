/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.dao;

import com.oncore.template.model.Module;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/26/16.
 */
@Repository("moduleDao")
public class ModuleDaoImpl  extends HibernateBaseGenericDaoImpl<Module,String> implements ModuleDao {
    /**
     * 构造方法，根据实例类自动获取实体类类型
     *
     * @param serverHibernateSessionFactory
     */
    @Autowired
    public ModuleDaoImpl(SessionFactory serverHibernateSessionFactory) {
        super(serverHibernateSessionFactory);
    }

    @Override
    public void delete(Module entity) {
        super.delete(entity);
    }

    @Override
    public Module get(String id) {
        return super.get(id);
    }

    @Override
    public Module load(String id) {
        return super.load(id);
    }

    @Override
    public List<Module> loadAll() {
        return super.loadAll();
    }

    @Override
    public void save(Module entity) {
        super.save(entity);
    }

    @Override
    public void saveOrUpdate(Module entity) {
        super.saveOrUpdate(entity);
    }

    @Override
    public void update(Module entity) {
        super.update(entity);
    }

    @Override
    public Module getbyParam(String param, Object value) {
        return super.getbyParam(param, value);
    }

    @Override
    public List<Module> getListbyParam(String param, Object value) {
        return super.getListbyParam(param, value);
    }

    @Override
    public List<Module> getListbyField(String[] fields) {
        return super.getListbyField(fields);
    }

    @Override
    public List<Module> getListbyParams(Map<String, Object> map) {
        return super.getListbyParams(map);
    }

    @Override
    public List<Module> getListbyFieldAndParams(String[] fields, Map<String, Object> map) {
        return super.getListbyFieldAndParams(fields, map);
    }
}
