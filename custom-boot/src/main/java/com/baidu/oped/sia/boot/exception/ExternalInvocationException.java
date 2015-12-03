package com.baidu.oped.sia.boot.exception;

/**
 * External Service Invocation Exception
 * Created by mason on 12/3/15.
 */
public class ExternalInvocationException extends InternalException {

    public ExternalInvocationException(String message, Object[] args) {
        super(message, args);
    }
}
