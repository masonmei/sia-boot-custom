package com.baidu.oped.sia.boot.exception.request;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.UNSUPPORTED_REQUEST;

/**
 * Bad Request Exception.
 *
 * @author mason
 */
public class UnsupportRequestException extends RequestException {

    /**
     * Request Exception Constructor.
     *
     * @param args the key params
     */
    public UnsupportRequestException(Object[] args) {
        super(UNSUPPORTED_REQUEST, args);
    }
}
