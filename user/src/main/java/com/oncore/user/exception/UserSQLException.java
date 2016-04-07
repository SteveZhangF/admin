package com.oncore.user.exception;

import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * Created by steve on 3/8/16.
 */
/**
 * all user things are moved to user project
 *
 * */
public class UserSQLException extends BusinessException{
    public UserSQLException( String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
