package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.AUTHORIZATION_ERROR;

/**
 * Authorization Failed Exception.
 *
 * @author mason
 */
public class AuthorizationFailedException extends AccessDeniedException {

    public AuthorizationFailedException(String message) {
        super(AUTHORIZATION_ERROR, message);
    }
}
