package com.oncore.common.message;

import com.oncore.common.exception.BusinessException;
import com.oncore.common.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 3/5/16.
 */
public class ValidationExceptionMessage extends ExceptionMessage {
    private Map error_validations = new HashMap<>();
    public ValidationExceptionMessage(ValidationException e) {
        super(e);
        this.error_validations = e.getFields();
    }
}
