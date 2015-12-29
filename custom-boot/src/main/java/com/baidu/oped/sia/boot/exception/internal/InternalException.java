package com.baidu.oped.sia.boot.exception.internal;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.INTERNAL_SYS_ERROR;
import static com.baidu.oped.sia.boot.exception.SystemCode.INTERNAL_ERROR;

import com.baidu.oped.sia.boot.exception.SystemException;

/**
 * Internal Exception such as external service invocation etc.
 *
 * @author mason
 */
public class InternalException extends SystemException {

    public InternalException() {
        this(INTERNAL_SYS_ERROR, null);
    }

    public InternalException(String message, Object[] args) {
        super(INTERNAL_ERROR, message, args);
    }
}
