package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_MISSING;

/**
 * Parameter Exception when missing some param.
 *
 * @author mason
 */
public class MissParameterException extends ParameterException {

    public MissParameterException(Object[] args) {
        super(PARAM_MISSING, args);
    }
}
