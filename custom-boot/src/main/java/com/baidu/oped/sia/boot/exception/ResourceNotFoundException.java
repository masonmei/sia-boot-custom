package com.baidu.oped.sia.boot.exception;

/**
 * Created by mason on 12/3/15.
 */
public class ResourceNotFoundException extends SystemException {

    public ResourceNotFoundException(SystemCode code, String message, Object[] args) {
        super(code, message, args);
    }

    public ResourceNotFoundException(SystemCode code, String message) {
        super(code, message);
    }
}
