package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.DATA_SERVICE_ERROR;

/**
 * Data Service Exception.
 * <p>
 *
 * @author mason
 */
public class DataServiceException extends InternalException {

    public DataServiceException() {
        this(null);
    }

    public DataServiceException(Object[] args) {
        super(DATA_SERVICE_ERROR, args);
    }
}
