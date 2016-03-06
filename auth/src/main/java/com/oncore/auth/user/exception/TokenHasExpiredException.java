package com.oncore.auth.user.exception;

import com.oncore.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
public class TokenHasExpiredException extends BusinessException {

    public TokenHasExpiredException() {
        super(HttpStatus.FORBIDDEN, "Token has expired, An attempt was made to load a token that has expired");
    }
}
