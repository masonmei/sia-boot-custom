package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.INVALID_PARAMETER_VALUE;

/**
 * Parameter Exception when param value is invalid.
 *
 * @author mason
 */
public abstract class ParameterValueException extends SystemException {

    public ParameterValueException(String message, Object[] args) {
        super(INVALID_PARAMETER_VALUE, message, args);
    }
}
