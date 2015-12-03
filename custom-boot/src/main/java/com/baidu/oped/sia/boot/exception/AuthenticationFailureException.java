package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.AUTHENTICATION_ERROR;

/**
 * Created by mason on 12/3/15.
 */
public class AuthenticationFailureException extends AccessDeniedException {

    public AuthenticationFailureException(String message) {
        super(AUTHENTICATION_ERROR, message);
    }
}
