/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.service;

import com.oncore.common.exception.ValidationException;
import com.oncore.template.dao.IBaseGenericDao;
import com.oncore.template.model.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by steve on 10/12/15.
 */

public class BaseGenericServiceImpl<T extends Element, PK extends Serializable> implements IBaseGenericService<T, PK> {

    protected Validator validator;

    public BaseGenericServiceImpl(Validator validator) {
        this.validator = validator;
    }

    protected void validate(Object request) {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ValidationException(constraintViolations);
        }
    }


    @Autowired
    @Qualifier("hibernateBaseGenericDao")
    private IBaseGenericDao hibernateBaseGenericDao;

    public void delete(T entity) {
        hibernateBaseGenericDao.delete(entity);
    }

    public T get(PK id) {
        return (T) hibernateBaseGenericDao.get(id);
    }

    public T load(PK id) {
        return (T) hibernateBaseGenericDao.load(id);
    }

    public List<T> loadAll() {
        return hibernateBaseGenericDao.loadAll();
    }

    public void save(T entity) {
        hibernateBaseGenericDao.save(entity);
    }

    public void saveOrUpdate(T entity) {
        hibernateBaseGenericDao.saveOrUpdate(entity);
    }

    public void update(T entity) {
        hibernateBaseGenericDao.update(entity);
    }
}

