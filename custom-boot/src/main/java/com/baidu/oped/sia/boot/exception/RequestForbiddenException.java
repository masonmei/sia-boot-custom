package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.IP_ADDRESS_BLOCKED;
import static com.baidu.oped.sia.boot.exception.SystemCode.ACCESS_DENIED;

/**
 * Created by mason on 12/3/15.
 */
public class RequestForbiddenException extends AccessDeniedException {
    public RequestForbiddenException() {
        super(ACCESS_DENIED, IP_ADDRESS_BLOCKED);
    }
}
