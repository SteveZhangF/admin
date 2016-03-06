package com.oncore.auth.user.exception;

import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * Created by steve on 3/4/16.
 */
public class BadClientCredentialsException extends BusinessException {
    public BadClientCredentialsException(String msg) {
        super(HttpStatus.UNAUTHORIZED, "Client Credentials were incorrect, Client Credentials were incorrect. Useage: Base64Encode(username:password) ");
    }
}
