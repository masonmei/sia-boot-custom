package com.baidu.oped.sia.boot.exception;

/**
 * Created by mason on 10/15/15.
 */
public abstract class ExceptionKeyProvider {
    public static final String INTERNAL_SYS_ERROR = "system.internal.error";
    public static final String DATA_SERVICE_ERROR = "system.internal.data.error";
    public static final String EXTERNAL_SERVICE_ERROR = "system.internal.external.error";


    public static final String REQ_PARAM_MISMATCH = "request.param.mismatch";

    public static final String AUTH_USER_NOT_AUTHENTICATED = "invalid.param.auth.user.not-authenticated";
    public static final String AUTH_INVALID_USER = "invalid.param.auth.user.invalid";
    public static final String AUTH_SERVICE_NOT_AUTHENTICATED = "invalid.param.auth.service.not-authenticated";
    public static final String AUTH_INVALID_SERVICE = "invalid.param.auth.service.invalid";

    public static final String TOO_MANY_REQUEST = "access.denied.too.many.request";
    public static final String IP_ADDRESS_BLOCKED = "access.denied.ip.forbidden";

    public static final String PARAM_MISSING = "invalid.param.missing";
    public static final String PARAM_CONFLICT = "invalid.param.conflict";
    public static final String WRONG_PARAM_VALUE = "invalid.param.value.wrong";
    public static final String PARAM_VALUE_NOT_OF_SCOPE = "invalid.param.value.out-of-scope";
    public static final String PARAM_VALUE_OUT_OF_RANGE = "invalid.param.value.out-of-range";
    public static final String PARAM_VALUE_MISSING = "invalid.param.value.missing";
    public static final String PARAM_VALUE_UNMODIFIABLE = "invalid.param.value.unmodifiable";
    public static final String PARAM_VALUE_FORMAT = "invalid.param.value.unmodifiable";

}