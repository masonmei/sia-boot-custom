package com.baidu.oped.sia.boot.common;

import com.baidu.oped.sia.boot.exception.SystemCode;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Basic Response for all request.
 *
 * @author mason
 */
public class BasicResponse<T> {
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

    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Basic Response Builder.
     *
     * @param <T> data type
     */
    public static class Builder<T> {
        private String requestId;
        private Long traceStartTime;
        private String traceSourceIp;
        private int traceSourceSeq;
        private SystemCode code = SystemCode.OK;
        private String message = "";
        private T data;

        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder code(SystemCode code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder traceStartTime(Long timestamp) {
            this.traceStartTime = timestamp;
            return this;
        }

        public Builder traceSourceIp(String sourceIp) {
            this.traceSourceIp = sourceIp;
            return this;
        }

        public Builder traceSourceSeq(int sourceSeq) {
            this.traceSourceSeq = sourceSeq;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        /**
         * Build with context to BasicResponse.
         *
         * @return Basic Response.
         */
        public BasicResponse<T> build() {
            BasicResponse<T> basicResponse = new BasicResponse<>();
            if (requestId != null) {
                basicResponse.setRequestId(requestId);
            }

            if (traceStartTime != null) {
                basicResponse.setTraceStartTime(traceStartTime);
            }

            if (traceSourceIp != null) {
                basicResponse.setTraceSourceIp(traceSourceIp);
            }

            if (traceSourceSeq >= 0) {
                basicResponse.setTraceSourceSeq(traceSourceSeq);
            }

            if (code != null) {
                basicResponse.setCode(code);
            }

            if (message != null) {
                basicResponse.setMessage(message);
            }

            if (data != null) {
                basicResponse.setData(data);
            }
            return basicResponse;
        }
    }
}
