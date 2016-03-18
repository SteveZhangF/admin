package com.oncore.common.exception.handler;

import com.oncore.common.exception.BadRequestException;
import com.oncore.common.exception.BusinessException;
import com.oncore.common.exception.ValidationException;
import com.oncore.common.message.ExceptionMessage;
import com.oncore.common.message.NoHandlerFoundExceptionMessage;
import com.oncore.common.message.ValidationExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by steve on 3/5/16.
 */

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e){
        return new ResponseEntity(new ValidationExceptionMessage(e),e.getCode());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException e
    ) {
        return new ResponseEntity(new ExceptionMessage(e), e.getCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException e) {
        return new ResponseEntity(new NoHandlerFoundExceptionMessage(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handlerInvalidRequestIdException(HttpMessageNotReadableException e) {
        return  new ResponseEntity(new ExceptionMessage(new BadRequestException("Bad Request")),HttpStatus.BAD_REQUEST);

    }

}
