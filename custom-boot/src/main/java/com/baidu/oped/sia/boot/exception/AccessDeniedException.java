package com.baidu.oped.sia.boot.exception;

/**
 * Created by mason on 12/3/15.
 */
public class AccessDeniedException extends SystemException {

    public AccessDeniedException(SystemCode code, String message) {
        super(code, message);
    }
}
