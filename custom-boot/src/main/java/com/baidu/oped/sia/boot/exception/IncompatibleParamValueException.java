package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_VALUE_INCOMPATIBLE;

/**
 * Parameter Exception when param value is incompatible.
 *
 * @author mason
 */
public class IncompatibleParamValueException extends ParameterValueException {

    public IncompatibleParamValueException(Object[] args) {
        super(PARAM_VALUE_INCOMPATIBLE, args);
    }
}
