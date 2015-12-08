package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.EXTERNAL_SERVICE_ERROR;

/**
 * External Service Invocation Exception.
 *
 * @author mason
 */
public class ExternalInvocationException extends InternalException {

    public ExternalInvocationException() {
        this(null);
    }

    public ExternalInvocationException(Object[] args) {
        super(EXTERNAL_SERVICE_ERROR, args);
    }
}
