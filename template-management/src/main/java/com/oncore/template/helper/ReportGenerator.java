package com.oncore.template.helper;

import com.oncore.template.dao.FieldDao;
import com.oncore.template.dao.ReportFieldDao;
import com.oncore.template.model.Field;
import com.oncore.template.model.file.Report;
import com.oncore.template.model.file.ReportField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by steve on 3/9/16.
 */
@Component
@Transactional
public class ReportGenerator {


    @Autowired
    FieldDao fieldDao;
    @Autowired
    ReportFieldDao reportFieldDao;

    /**
     * parse the report content and generate the related report fields
     */
    public Report parseHTML(Report report, String html) {
        Document document = Jsoup.parse(html);
        org.jsoup.select.Elements elements = document.select("field");
        for (Element element : elements) {
            //if has entity id and no id, it's a new related field
            String field_id = element.attr("entity_field_id");
            System.out.println("find a related field |  "+field_id);
            String id = element.attr("id");
            String name = element.attr("name");
            Field field;
            if(id == null || id.replace(" ","").equalsIgnoreCase("")){
                if (field_id != null && (field = fieldDao.get(field_id)) != null) {
                    System.out.println("find a related field |  "+field_id);
                    ReportField reportField = new ReportField();
                    reportField.setFieldType(field.getFieldType());
                    reportField.setName(name);
                    reportField.setIfNull(field.isIfNull());
                    reportField.setIfRelatedField(true);
                    reportField.setLength(field.getLength());
                    reportField.setRelatedField(field);
                    report.addField(reportField);
                    reportField.setReport(report);
                    reportFieldDao.save(reportField);
                    reportField.setName(name + "_" +reportField.getId());
                    reportFieldDao.update(reportField);
                    element.attr("id",reportField.getId());
                    element.text("${" + name + "_" + reportField.getId() + "}");
                }
            }else {
                ReportField reportField = reportFieldDao.get(id);
                report.addField(reportField);
                reportField.setReport(report);
                element.text("${" + name + "_" + reportField.getId() + "}");
            }

//             else {
//                ReportField reportField = new ReportField();
//                String type = element.attr("type");
//                String if_null = element.attr("if_null");
//                String length = element.attr("length");
//                reportField.setIfRelatedField(false);
//                reportField.setName(name);
//                if (if_null != null)
//                    reportField.setIfNull(Boolean.valueOf(if_null));
//                if (length != null)
//                    reportField.setLength(Integer.valueOf(length));
//                if (type != null)
//                    reportField.setFieldType(type);
//                reportField.setReport(report);
//                report.addField(reportField);
//                reportFieldDao.save(reportField);
//                reportField.setName(name + "_" +reportField.getId());
//                reportFieldDao.update(reportField);
//                element.text("${" + name + "_" + reportField.getId()  + "}");
//            }
        }
        report.setContent(document.outerHtml());
        return report;
    }
}
