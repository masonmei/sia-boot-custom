package com.baidu.oped.sia.boot.common;

import static com.baidu.oped.sia.boot.common.BasicResponse.builder;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceId;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceSourceIp;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceSourceSeq;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceStartTime;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(annotations = {RestController.class})
public class NormalizationResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BasicResponse) {
            return body;
        }
        return builder().requestId(getTraceId()).traceSourceSeq(getTraceSourceSeq()).traceSourceIp(getTraceSourceIp())
                .traceStartTime(getTraceStartTime()).data(body).build();
    }
}
