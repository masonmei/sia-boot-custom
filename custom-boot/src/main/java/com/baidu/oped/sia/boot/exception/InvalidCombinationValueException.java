package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_VALUE_COMBINATION_INVALID;

/**
 * Parameter Exception when param values combination is invalid.
 *
 * @author mason
 */
public class InvalidCombinationValueException extends ParameterValueException {

    public InvalidCombinationValueException(Object[] args) {
        super(PARAM_VALUE_COMBINATION_INVALID, args);
    }
}
