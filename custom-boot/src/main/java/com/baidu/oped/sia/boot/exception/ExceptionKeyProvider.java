package com.baidu.oped.sia.boot.exception;

/**
 * Created by mason on 10/15/15.
 */
public abstract class ExceptionKeyProvider {
    public static final String INTERNAL_SYS_ERROR = "system.internal.error";
    public static final String REQ_PARAM_MISMATCH = "request.param.mismatch";

    public static final String AUTH_USER_NOT_AUTHENTICATED = "invalid.param.auth.user.not-authenticated";
    public static final String AUTH_INVALID_USER = "invalid.param.auth.user.invalid";
    public static final String AUTH_SERVICE_NOT_AUTHENTICATED = "invalid.param.auth.service.not-authenticated";
    public static final String AUTH_INVALID_SERVICE = "invalid.param.auth.service.invalid";

    public static final String TOO_MANY_REQUEST = "access.denied.too.many.request";
    public static final String IP_ADDRESS_BLOCKED = "access.denied.ip.forbidden";
}