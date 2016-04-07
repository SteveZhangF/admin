package com.oncore.user.dao;

import com.oncore.common.groovy.GBaseDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by steve on 3/7/16.
 */
/**
 * all user things are moved to user project
 *
 * */
public abstract class GroovyDaoFactory  implements ApplicationContextAware {
     @Autowired
     public GroovyDaoFactory(){}
     protected ApplicationContext applicationContext;
     @Override
     public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext = applicationContext;
     }

     public abstract GBaseDao getDao(String id);

}
