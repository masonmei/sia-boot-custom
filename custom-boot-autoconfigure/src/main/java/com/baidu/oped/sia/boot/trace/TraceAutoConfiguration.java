package com.baidu.oped.sia.boot.trace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_PREFIX;

/**
 * Tracing ability to support tracing request from different systems. Enabled by default.
 * <p>
 * Created by mason on 10/30/15.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, HttpServletRequest.class, HttpServletResponse.class})
@ConditionalOnProperty(prefix = TRACE_PREFIX, name = ENABLED, matchIfMissing = true, havingValue = "true")
@EnableConfigurationProperties(TraceProperties.class)
public class TraceAutoConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private TraceProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(buildTraceInterceptor());
    }

    private HandlerInterceptor buildTraceInterceptor() {
        return new TraceInterceptor(properties.getTraceHeaderName(), properties.getTraceStartTimeHeaderName());
    }
}
