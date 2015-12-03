package com.baidu.oped.sia.boot.exception;

/**
 * Created by mason on 12/3/15.
 */
public class ParameterException extends SystemException {
    public ParameterException(SystemCode code, String message, Object[] args) {
        super(code, message, args);
    }
}
