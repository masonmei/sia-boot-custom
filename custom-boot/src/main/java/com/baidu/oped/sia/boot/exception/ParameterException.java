package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.INVALID_PARAMETER;

/**
 * Created by mason on 12/3/15.
 */
public abstract class ParameterException extends SystemException {
    public ParameterException(String message, Object[] args) {
        super(INVALID_PARAMETER, message, args);
    }
}
