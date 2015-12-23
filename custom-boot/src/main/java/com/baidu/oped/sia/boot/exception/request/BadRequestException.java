package com.baidu.oped.sia.boot.exception.request;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.BAD_REQUEST;

/**
 * Bad Request Exception.
 *
 * @author mason
 */
public class BadRequestException extends RequestException {
    /**
     * Request Exception Constructor.
     */
    public BadRequestException() {
        super(BAD_REQUEST, null);
    }
}
