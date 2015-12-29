package com.baidu.oped.sia.boot.exception.internal.database;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.DATA_SERVICE_ERROR;

import com.baidu.oped.sia.boot.exception.internal.InternalException;

/**
 * Data Service Exception.
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
