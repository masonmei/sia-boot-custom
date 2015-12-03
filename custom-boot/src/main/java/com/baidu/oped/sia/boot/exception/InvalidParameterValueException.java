package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.INVALID_PARAMETER_VALUE;

/**
 * Created by mason on 12/3/15.
 */
public class InvalidParameterValueException extends ParameterException {

    public InvalidParameterValueException(String message, Object[] args) {
        super(INVALID_PARAMETER_VALUE, message, args);
    }
}
