package com.baidu.oped.sia.boot.exception;

/**
 * Base exception for user not allow to visit the resource.
 *
 * @author mason
 */
public class AccessDeniedException extends SystemException {

    public AccessDeniedException(SystemCode code, String message) {
        super(code, message);
    }
}
