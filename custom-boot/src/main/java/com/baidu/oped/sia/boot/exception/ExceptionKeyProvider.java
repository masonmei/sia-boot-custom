package com.baidu.oped.sia.boot.exception;

/**
 * Exception Key Provider.
 *
 * @author mason
 */
public abstract class ExceptionKeyProvider {
    public static final String INTERNAL_SYS_ERROR = "system.internal.error";
    public static final String DATA_SERVICE_ERROR = "system.internal.data.error";
    public static final String EXTERNAL_SERVICE_ERROR = "system.internal.external.error";

    public static final String AUTH_USER_NOT_AUTHENTICATED = "auth.error.user.not-authenticated";
    public static final String AUTH_INVALID_USER = "auth.error.user.invalid";
    public static final String AUTH_SERVICE_NOT_AUTHENTICATED = "auth.error.service.not-authenticated";
    public static final String AUTH_INVALID_SERVICE = "auth.error.service.invalid";

    public static final String SERVER_BUSY = "access.denied.server-busy";
    public static final String TOO_MANY_REQUEST = "access.denied.too.many.request";
    public static final String IP_ADDRESS_BLOCKED = "access.denied.ip.forbidden";

    public static final String PARAM_MISSING = "invalid.param.missing";
    public static final String PARAM_CONFLICT = "invalid.param.conflict";
    public static final String PARAM_VALUE_NOT_OF_SCOPE = "invalid.param.value.out-of-scope";
    public static final String PARAM_VALUE_OUT_OF_RANGE = "invalid.param.value.out-of-range";
    public static final String PARAM_VALUE_MISSING = "invalid.param.value.missing";
    public static final String PARAM_VALUE_UNMODIFIABLE = "invalid.param.value.unmodifiable";
    public static final String PARAM_VALUE_FORMAT = "invalid.param.value.format";
    public static final String PARAM_VALUE_COMBINATION_INVALID = "invalid.param.value.invalid.combination";
    public static final String PARAM_VALUE_INCOMPATIBLE = "invalid.param.value.incompatible";

    public static final String INVALID_CONFIGURATION_FILE_PATH = "config.file.invalid.path";
    public static final String CONFIGURATION_FILE_NOT_FOUND = "config.file.not-found";

    public static final String RESOURCE_NOT_FOUND = "resource.error.not-found";

    public static final String BAD_REQUEST = "request.error.bad-request";
    public static final String UNSUPPORTED_REQUEST = "request.error.un-supported";
}
