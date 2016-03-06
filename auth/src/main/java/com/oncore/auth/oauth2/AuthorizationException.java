package com.oncore.auth.oauth2;


import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 25/04/2013
 */
public class AuthorizationException extends BusinessException {

    public AuthorizationException(String applicationMessage) {
        super(HttpStatus.FORBIDDEN, "Not authorized, "+  applicationMessage);
    }

}
