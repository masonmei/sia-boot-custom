package com.baidu.oped.sia.boot.trace;

import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.utils.Constrains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.UUID;

/**
 * Trace Interceptor for tracing web transactions
 * <p>
 * Created by mason on 10/30/15.
 */
public class TraceInterceptor extends HandlerInterceptorAdapter implements Ordered {
    private static final Logger log = LoggerFactory.getLogger(TraceInterceptor.class);

    private final String traceHeaderName;
    private final String traceTimestampHeaderName;

    public TraceInterceptor(String traceHeaderName, String traceTimestampHeaderName) {
        if (StringUtils.isEmpty(traceHeaderName)) {
            traceHeaderName = Constrains.TRACE_HEADER_NAME;
        }

        if (StringUtils.isEmpty(traceTimestampHeaderName)) {
            traceTimestampHeaderName = Constrains.TRACE_TIMESTAMP_HEADER_NAME;
        }

        this.traceHeaderName = traceHeaderName;
        this.traceTimestampHeaderName = traceTimestampHeaderName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (null == RequestInfoHolder.traceId()) {
            String requestId = request.getHeader(traceHeaderName);
            if (requestId == null) {
                requestId = UUID.randomUUID().toString();
                log.info("{} not found in header, generate traceId: {} ", traceHeaderName, requestId);
            }
            RequestInfoHolder.setTraceId(requestId);
            response.addHeader(traceHeaderName, requestId);
            MDC.put("requestId", requestId);
        }

        if (null == RequestInfoHolder.traceTimestamp()) {
            String currentTimeInMillis = request.getHeader(traceTimestampHeaderName);
            if (currentTimeInMillis == null) {
                currentTimeInMillis = String.format("%d", Calendar.getInstance().getTimeInMillis());
                RequestInfoHolder.setTraceTimestamp(currentTimeInMillis);
                log.info("{} not found in header, generate traceStartTimestamp: {}",
                        traceTimestampHeaderName, currentTimeInMillis);
            }
            response.addHeader(traceTimestampHeaderName, currentTimeInMillis);
            MDC.put("traceTimestamp", currentTimeInMillis);
        }


        log.info("request preHandle, method: {}, url: {}", request.getMethod(), request.getRequestURI());
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
            log.error("traceId changed, traceId: {}, responseTraceId: {}", threadTraceId, responseTraceId);
        }
        response.setHeader(traceHeaderName, threadTraceId);

        if (!threadTraceTimestamp.equals(responseTraceTimestamp)) {
            log.error("traceTimestamp changed, traceTimestamp: {}, responseTimestamp: {}",
                    threadTraceTimestamp, responseTraceTimestamp);
        }
        response.setHeader(traceTimestampHeaderName, threadTraceTimestamp);

        long timeUsed = Calendar.getInstance().getTimeInMillis() - Long.parseLong(responseTraceTimestamp);
        log.info("request afterCompletion, method: {}, url: {}, status: {}, time: {}ms", request.getMethod(),
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
