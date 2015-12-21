package com.baidu.oped.sia.boot.common;

import com.baidu.oped.sia.boot.exception.SystemCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Basic Response for all request.
 *
 * @author mason
 */
public class BasicResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long traceStartTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceSourceIp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int traceSourceSeq;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SystemCode code = SystemCode.OK;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message = "";

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getTraceStartTime() {
        return traceStartTime;
    }

    public void setTraceStartTime(Long traceStartTime) {
        this.traceStartTime = traceStartTime;
    }

    public String getTraceSourceIp() {
        return traceSourceIp;
    }

    public void setTraceSourceIp(String traceSourceIp) {
        this.traceSourceIp = traceSourceIp;
    }

    public int getTraceSourceSeq() {
        return traceSourceSeq;
    }

    public void setTraceSourceSeq(int traceSourceSeq) {
        this.traceSourceSeq = traceSourceSeq;
    }

    public SystemCode getCode() {
        return code;
    }

    public void setCode(SystemCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
