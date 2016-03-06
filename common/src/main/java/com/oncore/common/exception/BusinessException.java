package com.oncore.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by steve on 2/25/16.
 */
public class BusinessException extends RuntimeException {

    private HttpStatus code;

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(HttpStatus code, String msg){
        super(msg);
        this.code = code;
    }
}
