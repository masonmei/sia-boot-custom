package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.RESOURCE_NOT_EXIST;

/**
 * Exception when the requesting resource not exist.
 *
 * @author mason
 */
public class ResourceNotFoundException extends SystemException {

    public ResourceNotFoundException(String message) {
        this(message, null);
    }

    public ResourceNotFoundException(String message, Object[] args) {
        super(RESOURCE_NOT_EXIST, message, args);
    }
}
