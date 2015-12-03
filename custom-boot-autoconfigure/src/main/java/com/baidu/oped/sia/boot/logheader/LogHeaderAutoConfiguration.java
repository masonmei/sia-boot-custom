/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.logheader;

import com.baidu.oped.sia.boot.common.FilterOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.LOG_HEADER_PREFIX;

/**
 * Created by meidongxu on 7/2/15.
 */
@Configuration
@ConditionalOnProperty(prefix = LOG_HEADER_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(LogHeaderProperties.class)
@ConditionalOnWebApplication
public class LogHeaderAutoConfiguration {

    @Autowired
    private LogHeaderProperties properties;

    @Bean
    public FilterRegistrationBean statisticsCollectingFilter() {
        LogHeaderRequestFilter filter = new LogHeaderRequestFilter(properties.getHeaderNames());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(FilterOrder.getOrder(FilterOrder.LOG_HEADER));
        return registrationBean;
    }
}
