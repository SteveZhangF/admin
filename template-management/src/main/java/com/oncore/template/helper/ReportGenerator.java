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

    public Report parseHTML(Report report, String html) {
//        String html = "<p>&nbsp;</p>\n" +
//                "<p style=\"text-align: center; font-size: 15px;\"><img src=\"images/glyph-tinymce@2x.png\" alt=\"TinyMCE\" width=\"110\" height=\"97\" /></p>\n" +
//                "<p style=\"text-align: center; color: #7e7e7e; font-size: 15px; font-family: avenir; font-weight: 200;\">TinyMCE is a platform independent web-based JavaScript HTML WYSIWYG<br /> editor control released as open source under LGPL.</p>\n" +
//                "<p style=\"text-align: center; color: #868686; font-size: 15px; font-family: avenir; font-weight: 200;\"><em>TinyMCE enables you to convert HTML textarea fields or other HTML elements to editor instances.</em></p>\n" +
//                "<p>&nbsp;</p><field id=\"123\" new_element=\"false\"></field>";
        Document document = Jsoup.parse(html);
        org.jsoup.select.Elements elements = document.select("field[id]");
        for (Element element : elements) {
            String field_id = element.attr("id");
            String name = element.attr("name");
            Field field = null;
            if (field_id != null && (field = fieldDao.get(field_id)) != null) {
                ReportField reportField = new ReportField();
                reportField.setFieldType(field.getFieldType());
                reportField.setName(name+"_"+field_id);
                reportField.setIfNull(field.isIfNull());
                reportField.setIsRelatedField(true);
                reportField.setLength(field.getLength());
                reportField.setRelatedField(field);
                report.addField(reportField);
                reportField.setReport(report);
                reportFieldDao.save(reportField);
                element.text("${"+name+"_"+field_id+"}");
//                element.val("${"+reportField.getId()+"}");
            } else {
                ReportField reportField = new ReportField();

                String type = element.attr("type");
                String if_null = element.attr("if_null");
                String length = element.attr("length");
                reportField.setIsRelatedField(false);
                reportField.setName(name);
                reportField.setLength(Integer.valueOf(length));
                reportField.setIfNull(Boolean.valueOf(if_null));
                reportField.setFieldType(type);
                reportField.setReport(report);
                report.addField(reportField);
                reportFieldDao.save(reportField);
//                element.val("${"+reportField.getId()+"}");
                element.text("${"+name+"}");
            }
        }
        report.setContent(document.outerHtml());
        return report;
    }

}
