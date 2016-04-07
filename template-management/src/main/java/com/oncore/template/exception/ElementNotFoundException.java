package com.oncore.template.exception;


import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * Created by steve on 1/20/16.
 */

/**
 * element not found exception
 * */
public class ElementNotFoundException extends com.oncore.common.exception.BusinessException {
    public ElementNotFoundException(String element) {
            super(HttpStatus.NOT_FOUND,"No "+ element + "could found");
    }

}
