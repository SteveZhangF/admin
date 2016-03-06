package com.oncore.template.dao;

import com.oncore.template.model.file.ReportField;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/22/16.
 */
@Repository("reportFieldDao")
public class ReportFieldDaoImpl extends HibernateBaseGenericDaoImpl<ReportField, String> implements ReportFieldDao {
    /**
     * 构造方法，根据实例类自动获取实体类类型
     *
     * @param serverHibernateSessionFactory
     */
    @Autowired
    public ReportFieldDaoImpl(SessionFactory serverHibernateSessionFactory) {
        super(serverHibernateSessionFactory);
    }

    @Override
    public void delete(ReportField entity) {
        super.delete(entity);
    }

    @Override
    public ReportField get(String id) {
        return super.get(id);
    }

    @Override
    public ReportField load(String id) {
        return super.load(id);
    }

    @Override
    public List<ReportField> loadAll() {
        return super.loadAll();
    }

    @Override
    public void save(ReportField entity) {
        super.save(entity);
    }

    @Override
    public void saveOrUpdate(ReportField entity) {
        super.saveOrUpdate(entity);
    }

    @Override
    public void update(ReportField entity) {
        super.update(entity);
    }

    @Override
    public ReportField getbyParam(String param, Object value) {
        return super.getbyParam(param, value);
    }

    @Override
    public List<ReportField> getListbyParam(String param, Object value) {
        return super.getListbyParam(param, value);
    }

    @Override
    public List<ReportField> getListbyField(String[] fields) {
        return super.getListbyField(fields);
    }

    @Override
    public List<ReportField> getListbyParams(Map<String, Object> map) {
        return super.getListbyParams(map);
    }

    @Override
    public List<ReportField> getListbyFieldAndParams(String[] fields, Map<String, Object> map) {
        return super.getListbyFieldAndParams(fields, map);
    }
}
