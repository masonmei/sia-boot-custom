package com.baidu.oped.sia.boot.trace;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.TRACE_PREFIX;


import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Tracing ability to support tracing request from different systems. Enabled by default.
 * <p>
 *
 * @author mason
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, HttpServletRequest.class, HttpServletResponse.class})
@ConditionalOnProperty(prefix = TRACE_PREFIX, name = ENABLED, matchIfMissing = true, havingValue = "true")
@EnableConfigurationProperties(TraceProperties.class)
public class TraceAutoConfiguration extends WebMvcConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TraceAutoConfiguration.class);

    @Autowired
    private TraceProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOG.info("add trace interceptor");
        registry.addInterceptor(buildTraceInterceptor());
    }

    private HandlerInterceptor buildTraceInterceptor() {
        return new TraceInterceptor(properties.getTraceHeaderName(), properties.getTraceStartTimeHeaderName(),
                properties.getTraceSourceHeaderName(), properties.getTraceSourceSeqHeaderName());
    }
}
