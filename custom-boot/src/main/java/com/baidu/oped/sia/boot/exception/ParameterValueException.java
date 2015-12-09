package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.INVALID_PARAMETER_VALUE;

/**
 * Base Exception of invalid param value.
 *
 * @author mason
 */
public abstract class ParameterValueException extends SystemException {

    public ParameterValueException(String message, Object[] args) {
        super(INVALID_PARAMETER_VALUE, message, args);
    }
}
