package com.baidu.oped.sia.boot.common;

import java.util.Arrays;
import java.util.List;

/**
 * Filter order manager.
 *
 * @author mason
 */
public abstract class FilterOrder {
    public static final String IP_LIST = "ip_list";
    public static final String IP_LIMIT = "ip_limit";
    public static final String IAM = "iam";
    public static final String LOG_HEADER = "log_header";
    public static final String DECODE = "decode";

    private static final List<String> KEYS = Arrays.asList(IP_LIST, IP_LIMIT, DECODE, IAM, LOG_HEADER);

    public static int getOrder(String key) {
        return KEYS.indexOf(key) + 1;
    }
}
