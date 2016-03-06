package com.oncore.data.file;

import com.oncore.common.configure.CommonConfigure;
import com.oncore.common.model.TableElement;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 3/6/16.
 */
@Component("hibernateMappingFileCreator")
public class HibernateMappingFileCreator extends FileCreator<TableElement> {

    @Autowired
    public HibernateMappingFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(TableElement t) throws IOException {
        File file = new File(commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t.getHbmPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        String mappingFileName = t.getName() + ".xml";
        file = new File(commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t.getHbmPath() + "/" + mappingFileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        System.out.println(file.getAbsolutePath());
        return file;
    }

    @Override
    protected Template getTemplate() {
        try {
            Template template = configuration.getTemplate(commonConfigure.getTemplate_hibernate_mapping_file());
            return template;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected Map getElementRoot(TableElement t) {
        Map root = new HashMap();
        root.put("name", t.getName());
        root.put("tableName", t.getTableName());
        root.put("fields", t.getFields());
        return root;
    }
}
