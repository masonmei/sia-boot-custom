package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_CONFLICT;

/**
 * Parameter exception when conflict.
 * <p>
 *
 * @author mason
 */
public class ConflictParameterException extends ParameterException {
    public ConflictParameterException(Object[] args) {
        super(PARAM_CONFLICT, args);
    }
}
