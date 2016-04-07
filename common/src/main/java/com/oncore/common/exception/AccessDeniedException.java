package com.oncore.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by steve on 2/24/16.
 */
/**
 * access denied exception
 * */
public class AccessDeniedException extends BusinessException {
    public AccessDeniedException() {
        super(HttpStatus.FORBIDDEN,"You do not have permission to this resource!");
    }
}
