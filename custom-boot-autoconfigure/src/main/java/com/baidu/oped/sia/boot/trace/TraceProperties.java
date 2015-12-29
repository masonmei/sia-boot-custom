package com.baidu.oped.sia.boot.trace;

import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_HEADER_NAME;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_PREFIX;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_SOURCE_HEADER_NAME;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_SOURCE_SEQUENCE_HEADER_NAME;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_TIMESTAMP_HEADER_NAME;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for Tracing purpose.
 *
 * @author mason
 */
@ConfigurationProperties(prefix = TRACE_PREFIX)
public class TraceProperties {

    private boolean enabled = true;

    private String traceHeaderName = TRACE_HEADER_NAME;
    private String traceStartTimeHeaderName = TRACE_TIMESTAMP_HEADER_NAME;
    private String traceSourceHeaderName = TRACE_SOURCE_HEADER_NAME;
    private String traceSourceSeqHeaderName = TRACE_SOURCE_SEQUENCE_HEADER_NAME;

    public String getTraceHeaderName() {
        return traceHeaderName;
    }

    public void setTraceHeaderName(String traceHeaderName) {
        this.traceHeaderName = traceHeaderName;
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

    public String getTraceStartTimeHeaderName() {
        return traceStartTimeHeaderName;
    }

    public void setTraceStartTimeHeaderName(String traceStartTimeHeaderName) {
        this.traceStartTimeHeaderName = traceStartTimeHeaderName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
