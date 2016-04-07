package com.oncore.user.controller;

import com.oncore.common.controller.BaseController;
import com.oncore.common.groovy.GBaseDao;
import com.oncore.user.dao.GroovyDaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 3/7/16.
 */

/**
 * all user things are moved to user project
 *
 * */

@RestController
public class UserEntityController extends BaseController {

    @Autowired
    @Qualifier("entityGroovyDaoFactory")
    GroovyDaoFactory groovyDaoFactory;


    @RequestMapping(value = "/user/entities/{entity_id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listEntities(@PathVariable String entity_id) throws SQLException {
        GBaseDao dao = groovyDaoFactory.getDao(entity_id);
        User user = this.loadUserFromSecurityContext();
        List list = dao.list("123");
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/entities/{entity_id}/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertEntity(@PathVariable String entity_id, @RequestBody Map map) throws SQLException {
        GBaseDao dao = groovyDaoFactory.getDao(entity_id);
        User user = this.loadUserFromSecurityContext();
        dao.insert(map, "123");
        return new ResponseEntity(HttpStatus.OK);
    }
}
