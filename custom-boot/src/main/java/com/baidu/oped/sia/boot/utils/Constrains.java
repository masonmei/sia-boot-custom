package com.baidu.oped.sia.boot.utils;

/**
 * Created by mason on 10/29/15.
 */
public abstract class Constrains {

    public static final String AUTHORIZATION = "Authorization";
    public static final String USER = "currentUser";
    public static final String HOST = "host";
    public static final String REMOTE_ADDRESS = "ip";

    public static final String CUSTOMER_BOOT_BASE = "custom.boot.";
    public static final String ENABLED = "enabled";

    public static final String SPRING_FOX_PREFIX = CUSTOMER_BOOT_BASE + "spring.fox";

    public static final String IP_PERMISSION_PREFIX = CUSTOMER_BOOT_BASE + "iplist";

    public static final String TRACE_PREFIX = CUSTOMER_BOOT_BASE + "trace";
    public static final String TRACE_HEADER_NAME = "x-trace-header-name";
    public static final String TRACE_TIMESTAMP_HEADER_NAME = "x-trace-timestamp-header-name";
    public static final String TRACE_SOURCE_HEADER_NAME = "x-trace-source-header-name";
    public static final String TRACE_SOURCE_SEQUENCE_HEADER_NAME = "x-trace-source-seq-header-name";

    public static final String ACCESS_LOG_PREFIX = CUSTOMER_BOOT_BASE + "access-log";

    public static final String ASYNC_PREFIX = CUSTOMER_BOOT_BASE + "async";

    public static final String PROFILE_PREFIX = CUSTOMER_BOOT_BASE + "profiling";

    public static final String LIMIT_PREFIX = CUSTOMER_BOOT_BASE + "limit";

    public static final String I18N_PREFIX = CUSTOMER_BOOT_BASE + "i18n";

    public static final String ARGS_PREFIX = CUSTOMER_BOOT_BASE + "args";

    public static final String SSL_DUAL_PREFIX = CUSTOMER_BOOT_BASE + "ssl.dual";

    public static final String COMPRESSION_PREFIX = CUSTOMER_BOOT_BASE + "compression";

    public static final String REWRITE_PREFIX = CUSTOMER_BOOT_BASE + "rewrite";

    public static final String IAM_PREFIX = CUSTOMER_BOOT_BASE + "iam";

    public static final String LOG_HEADER_PREFIX = CUSTOMER_BOOT_BASE + "log-header";

    public static final String VALIDATE_PREFIX = CUSTOMER_BOOT_BASE + "client-source";

    public static final String DECODE_URI_PREFIX = COMPRESSION_PREFIX + "decode-uri";
}
