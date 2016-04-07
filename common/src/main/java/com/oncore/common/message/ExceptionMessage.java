package com.oncore.common.message;

import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * Created by steve on 3/4/16.
 */


/**
 * exception message
 * */
public class ExceptionMessage {

    private int error_code;
    private String error_message;

    /**
     * create message from business exception
     * @param e business exception
     *
     *
     * */
    public ExceptionMessage(BusinessException e
    ){
        this.error_code = e.getCode().value();
        this.error_message = e.getMessage();
    }

    /**
     * @param status http code
     * @param message message to show
     * */
    public ExceptionMessage(HttpStatus status,String message
    ){
        this.error_code = status.value();
        this.error_message =message;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
