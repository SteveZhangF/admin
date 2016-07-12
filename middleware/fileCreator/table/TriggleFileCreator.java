package com.oncore.middleware.fileCreator.table;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.fileCreator.FileCreator;
import com.oncore.middleware.jms.message.sender.TableCreatorMessageSender;
import com.oncore.middleware.model.file.Report;
import com.oncore.middleware.model.file.ReportField;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 3/29/16.
 */


/**
 * triggerFileCreator is different from the other creator
 * when a entity is created, we should create the init trigger,
 * and when we create a report which is related to the entity, we should
 * add a trigger to the init trigger
 *
 * invoke:
 *      createFile(entity);
 *
 *      addTrigger(report);
 *
 * */
@Component("triggleFileCreator")
public class TriggleFileCreator extends FileCreator<String> {
    @Autowired
    TableCreatorMessageSender tableCreatorMessageSender;

    @Autowired
    public TriggleFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(String t) throws IOException {
        File file = new File(commonConfigure.getBaseDir() + "/" + commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t);
        if (!file.exists()) {
            file.mkdirs();
        }
        String triggleFileName = t + "_trigger.sql";
        file = new File(commonConfigure.getBaseDir() + "/" + commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t + "/" + triggleFileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        log.info("created database trigger  file for " + t + " at " + file.getAbsolutePath());
        return file;
    }

    @Override
    protected Template getTemplate() {
        try {
            Template template = configuration.getTemplate(commonConfigure.getTemplate_entity_triggler_file());
            return template;
        } catch (IOException e) {
            log.info("reading trigger template file error");
            return null;
        }
    }



    @Override
    protected Map getElementRoot(String t) {
        Map root = new HashMap();
        root.put("tableName", t);
        return root;
    }
}
