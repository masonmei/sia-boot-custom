package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.TOO_MANY_REQUEST;
import static com.baidu.oped.sia.boot.exception.SystemCode.ACCESS_DENIED;

/**
 * Exception for user requests meet the maximum limitation.
 * <p>
 * Created by mason on 12/3/15.
 */
public class TooManyRequestException extends AccessDeniedException {

    public TooManyRequestException() {
        super(ACCESS_DENIED, TOO_MANY_REQUEST);
    }
}
