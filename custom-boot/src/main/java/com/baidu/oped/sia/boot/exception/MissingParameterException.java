package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.SystemCode.INVALID_PARAMETER;

/**
 * Created by mason on 12/3/15.
 */
public class MissingParameterException extends ParameterException {

    public MissingParameterException(String message, Object[] args) {
        super(INVALID_PARAMETER, message, args);
    }
}
