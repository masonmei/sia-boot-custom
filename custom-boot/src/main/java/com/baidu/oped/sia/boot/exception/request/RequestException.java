package com.baidu.oped.sia.boot.exception.request;

import static com.baidu.oped.sia.boot.exception.SystemCode.REQUEST_ERROR;

import com.baidu.oped.sia.boot.exception.SystemException;

/**
 * Request Exceptions.
 *
 * @author mason
 */
public abstract class RequestException extends SystemException {

    /**
     * Request Exception Constructor.
     *
     * @param message exception msg key
     * @param args    exception msg args
     */
    public RequestException(String message, Object[] args) {
        super(REQUEST_ERROR, message, args);
    }
}
