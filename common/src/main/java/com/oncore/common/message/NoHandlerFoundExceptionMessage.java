package com.oncore.common.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by steve on 3/5/16.
 */
public class NoHandlerFoundExceptionMessage extends ExceptionMessage {
    public NoHandlerFoundExceptionMessage(NoHandlerFoundException e) {
        super(HttpStatus.NOT_FOUND,  "No handler found for " + e.getHttpMethod() + " " + e.getRequestURL());
    }

}
