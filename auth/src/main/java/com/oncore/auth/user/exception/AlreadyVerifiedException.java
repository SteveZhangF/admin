package com.oncore.auth.user.exception;


import org.springframework.http.HttpStatus;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
public class AlreadyVerifiedException extends com.oncore.common.exception.BusinessException {
    public AlreadyVerifiedException() {
        super(HttpStatus.CONFLICT, "The token has already been verified");
    }
}
