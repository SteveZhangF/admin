package com.oncore.common.exception;

import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by steve on 3/4/16.
 */

/**
 *
 * validation exception
 * extends from BusinessException
 *
 * */
public class ValidationException extends BusinessException {

    private Map fields = new HashMap<>();

    public ValidationException(Set<? extends ConstraintViolation<?>> constraints) {
        super(HttpStatus.BAD_REQUEST,"Bad Request");
        buildException(constraints);
    }

    private void buildException(Set<? extends ConstraintViolation<?>> constraints) {
        for (ConstraintViolation constraintViolation : constraints) {
            fields.put(constraintViolation.getPropertyPath().toString()+"  "+ (constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null),constraintViolation.getMessage());
        }
    }

    public Map getFields() {
        return fields;
    }

    public void setFields(Map fields) {
        this.fields = fields;
    }
}
