package com.oncore.template.dao;

import com.oncore.template.exception.ElementNotFoundException;
import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by steve on 1/20/16.
 */
@Repository("reportDao")
public class ReportDaoImpl extends HibernateBaseGenericDaoImpl<Report, String> implements ReportDao {
    /**
     * 构造方法，根据实例类自动获取实体类类型
     *
     * @param sessionFactory
     */
    @Autowired
    public ReportDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void delete(Report entity) {
        super.delete(entity);
    }

    @Override
    public Report get(String id) {

        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select  report from Report as report where report.deleted=false and report.id=:id ");
        query.setString("id", id);
        Report entity = (Report) query.uniqueResult();
        Query query1 = session.createQuery("select field from ReportField as field where field.report=:id and field.deleted=false");
        query1.setString("id", id);
        List<ReportField> fields = query1.list();
        if (entity != null) {
            entity.setFields(fields);
        }
        return entity;

    }

    @Override
    public Report load(String id) {
        return super.load(id);
    }

    @Override
    public List<Report> loadAll() {
        return super.loadAll();
    }

    @Override
    public void save(Report entity) {
        super.save(entity);
    }

    @Override
    public void saveOrUpdate(Report entity) {
        super.saveOrUpdate(entity);
    }

    @Override
    public void update(Report entity) {
        super.update(entity);
    }

    @Override
    public Report getbyParam(String param, Object value) {
        return super.getbyParam(param, value);
    }

    @Override
    public List<Report> getListbyParam(String param, Object value) {
        return super.getListbyParam(param, value);
    }

    @Override
    public List<Report> getListbyField(String[] fields) {
        return super.getListbyField(fields);
    }

    @Override
    public List<Report> getListbyParams(Map<String, Object> map) {
        return super.getListbyParams(map);
    }

    @Override
    public List<Report> getListbyFieldAndParams(String[] fields, Map<String, Object> map) {
        return super.getListbyFieldAndParams(fields, map);
    }

    @Override
    public void deleteReport(String id) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update Report report set report.deleted=true where report.id=:id");
        query.setString("id", id);
        int i = query.executeUpdate();
        if (i == 0) {
            throw new ElementNotFoundException("report");
        }
        session.getTransaction().commit();
    }

    @Override
    public String getReportTableName(String id) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("select report.tableName from Report report where report.id=:id");
        query.setString("id", id);
        String tableName = (String) query.uniqueResult();
        return tableName;
    }

    @Override
    public void setFolder(String folderId,String reportId) {
        Report report = checkExist(reportId);
        report.setParentFoldId(folderId);
        this.update(report);

    }

    private Report checkExist(String id){
        Report report = this.get(id);
        if(null == report){
            throw new ElementNotFoundException(" report ");
        }
        return report;
    }
}
