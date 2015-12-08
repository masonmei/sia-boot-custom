package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_VALUE_FORMAT;

/**
 * Parameter Exception when param value is out of given range.
 *
 * @author mason
 */
public class ParamValueFormatException extends ParameterValueException {

    public ParamValueFormatException(Object[] args) {
        super(PARAM_VALUE_FORMAT, args);
    }
}
