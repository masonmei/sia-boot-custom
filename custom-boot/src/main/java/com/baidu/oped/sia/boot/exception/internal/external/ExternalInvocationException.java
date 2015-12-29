package com.baidu.oped.sia.boot.exception.internal.external;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.EXTERNAL_SERVICE_ERROR;

import com.baidu.oped.sia.boot.exception.internal.InternalException;

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
