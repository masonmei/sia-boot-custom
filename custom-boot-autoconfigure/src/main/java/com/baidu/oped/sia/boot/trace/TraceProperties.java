package com.baidu.oped.sia.boot.trace;

import com.baidu.oped.sia.boot.utils.Constrains;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for Tracing purpose.
 * <p>
 *
 * @author mason
 */
@ConfigurationProperties(prefix = Constrains.TRACE_PREFIX)
public class TraceProperties {

    private boolean enabled = true;

    private String traceHeaderName = Constrains.TRACE_HEADER_NAME;
    private String traceStartTimeHeaderName = Constrains.TRACE_TIMESTAMP_HEADER_NAME;
    private String traceSourceHeaderName = Constrains.TRACE_SOURCE_HEADER_NAME;
    private String traceSourceSeqHeaderName = Constrains.TRACE_SOURCE_SEQUENCE_HEADER_NAME;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTraceHeaderName() {
        return traceHeaderName;
    }

    public void setTraceHeaderName(String traceHeaderName) {
        this.traceHeaderName = traceHeaderName;
    }

    public String getTraceStartTimeHeaderName() {
        return traceStartTimeHeaderName;
    }

    public void setTraceStartTimeHeaderName(String traceStartTimeHeaderName) {
        this.traceStartTimeHeaderName = traceStartTimeHeaderName;
    }

    public String getTraceSourceHeaderName() {
        return traceSourceHeaderName;
    }

    public void setTraceSourceHeaderName(String traceSourceHeaderName) {
        this.traceSourceHeaderName = traceSourceHeaderName;
    }

    public String getTraceSourceSeqHeaderName() {
        return traceSourceSeqHeaderName;
    }

    public void setTraceSourceSeqHeaderName(String traceSourceSeqHeaderName) {
        this.traceSourceSeqHeaderName = traceSourceSeqHeaderName;
    }
}
