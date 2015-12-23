package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.RESOURCE_NOT_FOUND;
import static com.baidu.oped.sia.boot.exception.SystemCode.RESOURCE_NOT_EXIST;

/**
 * Exception when the requesting resource not exist.
 *
 * @author mason
 */
public class ResourceNotFoundException extends SystemException {

    public ResourceNotFoundException() {
        super(RESOURCE_NOT_EXIST, RESOURCE_NOT_FOUND, null);
    }
}
