package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_VALUE_MISSING;

/**
 * Parameter Exception when param value is not provided.
 *
 * @author mason
 */
public class MissParamValueException extends ParameterValueException {

    public MissParamValueException(Object[] args) {
        super(PARAM_VALUE_MISSING, args);
    }
}
