package com.oncore.auth.user.exception;


import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
public class TokenNotFoundException extends BusinessException {

    public TokenNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Token Not Found, No token could be found for that Id");
    }
}
