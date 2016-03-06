package com.oncore.auth.user.exception;

import com.oncore.common.exception.*;
import org.springframework.http.HttpStatus;


public class AuthenticationException extends BusinessException {
    public AuthenticationException() {
        super(HttpStatus.UNAUTHORIZED,  "Authentication Error. The username or password were incorrect");
    }
}
