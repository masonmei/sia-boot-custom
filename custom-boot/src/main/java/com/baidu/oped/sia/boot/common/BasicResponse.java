package com.baidu.oped.sia.boot.common;

import com.baidu.oped.sia.boot.exception.SystemCode;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Basic Response for all request.
 *
 * @author mason
 */
public class BasicResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SystemCode code = SystemCode.OK;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message = "";

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
