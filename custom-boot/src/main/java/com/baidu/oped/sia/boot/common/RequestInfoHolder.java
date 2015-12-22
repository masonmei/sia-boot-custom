package com.baidu.oped.sia.boot.common;

import org.springframework.core.NamedInheritableThreadLocal;

/**
 * Thread local request info holder.
 *
 * @author mason
 */
public class RequestInfoHolder {
    private static final ThreadLocal<Boolean> IN_WHITE_LIST = new NamedInheritableThreadLocal<>("White List");

    private static final ThreadLocal<Boolean> IGNORE_AUTH = new ThreadLocal<>();

    private static final ThreadLocal<String> TRACE_ID = new NamedInheritableThreadLocal<>("Trace Id");
    private static final ThreadLocal<Long> TRACE_TIMESTAMP = new NamedInheritableThreadLocal<>("Trace timestamp");
    private static final ThreadLocal<String> TRACE_SOURCE_IP = new NamedInheritableThreadLocal<>("Trace Source IP");
    private static final ThreadLocal<Integer> TRACE_SEQUENCE = new NamedInheritableThreadLocal<>("Trace Sequence");

    private static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

    public static String currentUser() {
        return CURRENT_USER.get();
    }

    public static Boolean ignoreAuth() {
        return IGNORE_AUTH.get() != null && IGNORE_AUTH.get();
    }

    public static Boolean inWhiteList() {
        return IN_WHITE_LIST.get() != null && IN_WHITE_LIST.get();
    }

    public static void removeCurrentUser() {
        CURRENT_USER.remove();
    }

    public static void removeIgnoreAuth() {
        IGNORE_AUTH.remove();
    }

    public static void removeInWhiteList() {
        IN_WHITE_LIST.remove();
    }

    public static void removeTraceId() {
        TRACE_ID.remove();
    }

    public static void removeTraceSequence() {
        TRACE_SEQUENCE.remove();
    }

    public static void removeTraceSourceIp() {
        TRACE_SOURCE_IP.remove();
    }

    public static void removeTraceTimestamp() {
        TRACE_TIMESTAMP.remove();
    }

    public static void setCurrentUser(String currentUser) {
        CURRENT_USER.set(currentUser);
    }

    public static void setIgnoreAuth(Boolean ignoreAuth) {
        IGNORE_AUTH.set(ignoreAuth);
    }

    public static void setInWhiteList(Boolean inWhiteList) {
        IN_WHITE_LIST.set(inWhiteList);
    }

    public static void setTraceId(String requestId) {
        TRACE_ID.set(requestId);
    }

    public static void setTraceSequence(Integer traceSequence) {
        TRACE_SEQUENCE.set(traceSequence);
    }

    public static void setTraceSourceIp(String traceSourceIp) {
        TRACE_SOURCE_IP.set(traceSourceIp);
    }

    public static void setTraceTimestamp(Long traceTimestamp) {
        TRACE_TIMESTAMP.set(traceTimestamp);
    }

    public static String traceId() {
        return TRACE_ID.get();
    }

    public static Integer traceSequence() {
        return TRACE_SEQUENCE.get();
    }

    public static String traceSourceIp() {
        return TRACE_SOURCE_IP.get();
    }

    public static Long traceTimestamp() {
        return TRACE_TIMESTAMP.get();
    }

}
