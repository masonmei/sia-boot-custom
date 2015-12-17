/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.VALIDATE_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by mason on 7/14/15.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = VALIDATE_PREFIX,
                       name = ENABLED,
                       havingValue = "true",
                       matchIfMissing = false)
@EnableConfigurationProperties({ClientSourceCheckingProperties.class})
public class ClientSourceCheckingAutoConfiguration {
    @Autowired
    private ClientSourceCheckingProperties properties;

    @Bean
    public ClientSourceCheckingHandler sourceCheckingHandler(ClientSourceWhiteLabel clientSourceWhiteLabel) {
        return new ClientSourceCheckingHandler(properties, clientSourceWhiteLabel);
    }

    @Bean
    @Profile({"DEV", "test"})
    public ClientSourceWhiteLabel testWhiteLabel() {
        return new ClientSourceWhiteLabel() {
            @Override
            public boolean isWhiteAddress(String address) {
                return false;
            }

            @Override
            public boolean isWhiteHost(String host) {
                return true;
            }
        };
    }

    @Bean
    @Profile({"default", "PROD"})
    public ClientSourceWhiteLabel whiteLabel() {
        return new DefaultClientSourceWhiteLabel(properties);
    }
}
