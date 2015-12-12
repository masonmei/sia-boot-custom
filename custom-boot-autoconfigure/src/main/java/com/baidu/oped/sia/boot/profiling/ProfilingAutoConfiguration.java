/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.profiling;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.PROFILE_PREFIX;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by meidongxu on 7/6/15.
 */
@Configuration
@ConditionalOnProperty(prefix = PROFILE_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(ProfilingProperties.class)
public class ProfilingAutoConfiguration {

    @Autowired
    private ProfilingProperties properties;

    @Bean
    public ProfilingAspect profilingInterceptor() {
        return new ProfilingAspect(properties.getWarningThreshold(), properties.getLogFrequency());
    }

}
