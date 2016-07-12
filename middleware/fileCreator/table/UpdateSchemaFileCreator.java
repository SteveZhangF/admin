package com.oncore.middleware.fileCreator.table;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.fileCreator.FileCreator;
import com.oncore.middleware.model.TableElement;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by steve on 4/7/16.
 */
@Component("updateSchemaFileCreator")
public class UpdateSchemaFileCreator extends FileCreator<Map>{

    Log log = LogFactory.getLog(UpdateSchemaFileCreator.class);

    @Autowired
    public UpdateSchemaFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(Map map) throws IOException {
        TableElement t = (TableElement) map.get("newTable");
        File file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t.getHbmPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        String createSchema = t.getName();
        file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t.getHbmPath() + "/" + createSchema);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        log.info("created update schema script file for "+t.getName()+" at " +file.getAbsolutePath());
        return file;
    }

    @Override
    protected Template getTemplate() {
        try {
            Template template = configuration.getTemplate(commonConfigure.getTemplate_update_schema_file());
            return template;
        } catch (IOException e) {
            log.info("reading update schema template file error | " + e.getMessage());
            return null;
        }

    }

    @Override
    protected Map getElementRoot(Map o) {
        return o;
    }
}
