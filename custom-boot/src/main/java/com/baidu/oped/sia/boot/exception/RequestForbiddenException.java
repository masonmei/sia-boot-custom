package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.IP_ADDRESS_BLOCKED;
import static com.baidu.oped.sia.boot.exception.SystemCode.ACCESS_DENIED;

/**
 * Access denied while the client ip is in the black list.
 *
 * @author mason
 */
public class RequestForbiddenException extends AccessDeniedException {
    public RequestForbiddenException() {
        super(ACCESS_DENIED, IP_ADDRESS_BLOCKED);
    }
}
