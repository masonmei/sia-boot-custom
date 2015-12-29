package com.baidu.oped.sia.boot.utils;

import com.baidu.oped.sia.boot.common.RequestInfoHolder;

/**
 * Request Utils.
 *
 * @author mason
 */
public abstract class RequestUtils {
    /**
     * Get the trace request id from RequestInfoHolder.
     *
     * @return trace id.
     */
    public static String getTraceId() {
        return RequestInfoHolder.traceId();
    }

    /**
     * Get the trace source client ip from RequestInfoHolder.
     *
     * @return trace source client ip.
     */
    public static String getTraceSourceIp() {
        return RequestInfoHolder.traceSourceIp();
    }

    /**
     * Get the trace source sequence from RequestInfoHolder.
     *
     * @return trace sequence.
     */
    public static int getTraceSourceSeq() {
        Integer integer = RequestInfoHolder.traceSequence();
        if (integer != null) {
            return integer;
        }
        return -1;
    }

    /**
     * Get the trace start timestamp from RequestInfoHolder.
     *
     * @return trace start timestamp in long
     */
    public static Long getTraceStartTime() {
        return RequestInfoHolder.traceTimestamp();
    }


}
