package com.baidu.oped.sia.boot.trace;

import static java.lang.String.format;

import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_SOURCE_HEADER_NAME;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_SOURCE_SEQUENCE_HEADER_NAME;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_TIMESTAMP_HEADER_NAME;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.UUID;

import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.utils.Constrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Trace Interceptor for tracing web transactions.
 * <p>
 *
 * @author mason
 */
public class TraceInterceptor extends HandlerInterceptorAdapter implements Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(TraceInterceptor.class);

    private final String traceHeaderName;
    private final String traceTimestampHeaderName;
    private final String traceSourceIpHeaderName;
    private final String traceSourceSeqHeaderName;

    public TraceInterceptor(String traceHeaderName, String traceTimestampHeaderName, String traceSourceIpHeaderName,
                            String traceSourceSeqHeaderName) {
        if (StringUtils.isEmpty(traceHeaderName)) {
            traceHeaderName = Constrains.TRACE_HEADER_NAME;
        }

        if (StringUtils.isEmpty(traceTimestampHeaderName)) {
            traceTimestampHeaderName = TRACE_TIMESTAMP_HEADER_NAME;
        }

        if (StringUtils.isEmpty(traceSourceIpHeaderName)) {
            traceSourceIpHeaderName = TRACE_SOURCE_HEADER_NAME;
        }

        if (StringUtils.isEmpty(traceSourceSeqHeaderName)) {
            traceSourceSeqHeaderName = TRACE_SOURCE_SEQUENCE_HEADER_NAME;
        }

        this.traceHeaderName = traceHeaderName;
        this.traceTimestampHeaderName = traceTimestampHeaderName;
        this.traceSourceIpHeaderName = traceSourceIpHeaderName;
        this.traceSourceSeqHeaderName = traceSourceSeqHeaderName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (null == RequestInfoHolder.traceId()) {
            String requestId = request.getHeader(traceHeaderName);
            if (requestId == null) {
                requestId = UUID.randomUUID().toString();
                LOG.info("{} not found in header, generate traceId: {} ", traceHeaderName, requestId);
            }
            RequestInfoHolder.setTraceId(requestId);
            response.addHeader(traceHeaderName, requestId);
            MDC.put("requestId", requestId);
        }

        if (null == RequestInfoHolder.traceTimestamp()) {
            String currentTimeInMillis = request.getHeader(traceTimestampHeaderName);
            if (currentTimeInMillis == null) {
                currentTimeInMillis = format("%d", Calendar.getInstance().getTimeInMillis());
                RequestInfoHolder.setTraceTimestamp(currentTimeInMillis);
                LOG.info("{} not found in header, generate traceStartTimestamp: {}",
                        traceTimestampHeaderName, currentTimeInMillis);
            }
            response.addHeader(traceTimestampHeaderName, currentTimeInMillis);
            MDC.put("traceTimestamp", currentTimeInMillis);
        }

        if (null == RequestInfoHolder.traceSourceIp()) {
            String sourceIp = request.getRemoteAddr();

            RequestInfoHolder.setTraceSourceIp(sourceIp);
            response.addHeader(traceSourceIpHeaderName, sourceIp);
            MDC.put("traceSourceIp", sourceIp);
        }

        if (null == RequestInfoHolder.traceSequence()) {
            int sequence = request.getIntHeader(traceSourceSeqHeaderName);
            RequestInfoHolder.setTraceSequence(sequence);
            response.addIntHeader(traceTimestampHeaderName, sequence);
            MDC.put("traceSourceSeq", format("%d", sequence));
        }

        LOG.info("request preHandle, method: {}, url: {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        String responseTraceId = response.getHeader(traceHeaderName);
        String responseTraceTimestamp = response.getHeader(traceTimestampHeaderName);

        String threadTraceId = RequestInfoHolder.traceId();
        String threadTraceTimestamp = RequestInfoHolder.traceTimestamp();

        if (!threadTraceId.equals(responseTraceId)) {
            LOG.error("traceId changed, traceId: {}, responseTraceId: {}", threadTraceId, responseTraceId);
        }
        response.setHeader(traceHeaderName, threadTraceId);

        if (!threadTraceTimestamp.equals(responseTraceTimestamp)) {
            LOG.error("traceTimestamp changed, traceTimestamp: {}, responseTimestamp: {}",
                    threadTraceTimestamp, responseTraceTimestamp);
        }
        response.setHeader(traceTimestampHeaderName, threadTraceTimestamp);

        long timeUsed = Calendar.getInstance().getTimeInMillis() - Long.parseLong(responseTraceTimestamp);
        LOG.info("request afterCompletion, method: {}, url: {}, status: {}, time: {}ms", request.getMethod(),
                request.getRequestURI(), response.getStatus(), timeUsed);
        MDC.remove("requestId");
        RequestInfoHolder.removeTraceTimestamp();
        RequestInfoHolder.removeTraceId();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
