package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.INVALID_PARAMETER;

/**
 * Base Exception of parameter exception.
 *
 * @author mason
 */
public abstract class ParameterException extends SystemException {
    public ParameterException(String message, Object[] args) {
        super(INVALID_PARAMETER, message, args);
    }
}
