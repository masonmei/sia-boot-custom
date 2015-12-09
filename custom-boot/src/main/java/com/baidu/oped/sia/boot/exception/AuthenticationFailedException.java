package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.AUTHENTICATION_ERROR;

/**
 * Authentication failed exception.
 * <p>
 *
 * @author mason
 */
public class AuthenticationFailedException extends AccessDeniedException {

    public AuthenticationFailedException(String message) {
        super(AUTHENTICATION_ERROR, message);
    }
}
