package com.oncore.auth.user.exception;

import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User Not Found, No User could be found for that Id");
    }
}
