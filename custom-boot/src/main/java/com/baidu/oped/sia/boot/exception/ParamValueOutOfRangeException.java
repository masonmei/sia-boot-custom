package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_VALUE_OUT_OF_RANGE;

/**
 * Parameter Exception when param value is out of given range.
 *
 * @author mason
 */
public class ParamValueOutOfRangeException extends ParameterValueException {

    public ParamValueOutOfRangeException(Object[] args) {
        super(PARAM_VALUE_OUT_OF_RANGE, args);
    }
}
