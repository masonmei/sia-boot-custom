package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_VALUE_UNMODIFIABLE;

/**
 * Parameter Exception when unmodifiable param value changed.
 *
 * @author mason
 */
public class UnmodifiableException extends ParameterValueException {

    public UnmodifiableException(Object[] args) {
        super(PARAM_VALUE_UNMODIFIABLE, args);
    }
}
