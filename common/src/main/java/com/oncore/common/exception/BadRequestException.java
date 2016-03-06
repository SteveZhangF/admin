package com.oncore.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by steve on 3/3/16.
 */
public class BadRequestException extends BusinessException {
    public BadRequestException(String msg) {
        super(HttpStatus.BAD_REQUEST,msg);
    }
}
