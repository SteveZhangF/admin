package com.oncore.user.dao;

import com.oncore.common.configure.CommonConfigure;
import com.oncore.common.groovy.GBaseDao;
import com.oncore.template.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by steve on 3/7/16.
 */

/**
 * all user things are moved to user project
 *
 * */
@Component("entityGroovyDaoFactory")
public class EntityGroovyDaoFactory extends GroovyDaoFactory {

    @Autowired
    EntityService entityService;

    @Override
    public GBaseDao getDao(String id) {
        String entityTableName = entityService.getEntityTableName(id);
        String daoBeanName = entityTableName + "_Dao";
        GBaseDao gBaseDao = (GBaseDao) this.applicationContext.getBean(daoBeanName);
        return gBaseDao;
    }
}
