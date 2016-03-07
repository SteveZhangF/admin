package com.oncore.common.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Created by steve on 3/4/16.
 */
@Configuration
public class CommonConfigure {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    private String baseDir;

    @Value("${template.base_path}")
    private String templateBasePath;
    @Value("${create_table_thread_number}")
    private int create_table_thread_number;
    @Value("${template.hibernate_mapping_file}")
    private String template_hibernate_mapping_file;

    @Value("${generated_file_destination.hibernate_mapping_file}")
    private String generated_file_destination_hibernate_mapping_file;
    @Value("${generated_file_destination.groovy_dao_file}")
    private String generated_file_destination_groovy_dao_file;
    @Value("${template.template_groovy_dao_file}")
    private String template_groovy_dao_file;

    public int getCreate_table_thread_number() {
        return create_table_thread_number;
    }

    public void setCreate_table_thread_number(int create_table_thread_number) {
        this.create_table_thread_number = create_table_thread_number;
    }

    public String getTemplateBasePath() {
        return templateBasePath;
    }

    public void setTemplateBasePath(String templateBasePath) {
        this.templateBasePath = templateBasePath;
    }



    public String getBaseDir() {
        if(baseDir == null){
            baseDir = System.getProperty("web.root");
            System.out.println(baseDir);
        }
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getTemplate_hibernate_mapping_file() {
        return template_hibernate_mapping_file;
    }

    public void setTemplate_hibernate_mapping_file(String template_hibernate_mapping_file) {
        this.template_hibernate_mapping_file = template_hibernate_mapping_file;
    }

    public String getGenerated_file_destination_hibernate_mapping_file() {
        return generated_file_destination_hibernate_mapping_file;
    }

    public void setGenerated_file_destination_hibernate_mapping_file(String generated_file_destination_hibernate_mapping_file) {
        this.generated_file_destination_hibernate_mapping_file = generated_file_destination_hibernate_mapping_file;
    }

    public String getTemplate_groovy_dao_file() {
        return template_groovy_dao_file;
    }

    public void setTemplate_groovy_dao_file(String template_groovy_dao_file) {
        this.template_groovy_dao_file = template_groovy_dao_file;
    }

    public String getGenerated_file_destination_groovy_dao_file() {
        return generated_file_destination_groovy_dao_file;
    }

    public void setGenerated_file_destination_groovy_dao_file(String generated_file_destination_groovy_dao_file) {
        this.generated_file_destination_groovy_dao_file = generated_file_destination_groovy_dao_file;
    }
}
