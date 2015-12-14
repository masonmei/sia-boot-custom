package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.INTERNAL_SYS_ERROR;
import static com.baidu.oped.sia.boot.exception.SystemCode.INTERNAL_ERROR;

/**
 * Internal Exception such as external service invocation etc
 * <p>
 * Created by mason on 12/3/15.
 */
public class InternalException extends SystemException {

    public InternalException() {
        this(INTERNAL_SYS_ERROR, null);
    }

    public InternalException(String message, Object[] args) {
        super(INTERNAL_ERROR, message, args);
    }
}
