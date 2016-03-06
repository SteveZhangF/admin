package com.oncore.auth.user.exception;


import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuplicateUserException extends BusinessException {

    public DuplicateUserException() {
        super(HttpStatus.CONFLICT,  "An attempt was made to create a user that already exists");
    }
}

