/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.logheader;

import static com.baidu.oped.sia.boot.utils.Constrains.LOG_HEADER_PREFIX;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LogHeader Configuration properties.
 *
 * @author mason
 */
@ConfigurationProperties(prefix = LOG_HEADER_PREFIX)
public class LogHeaderProperties {

    private boolean enabled = false;
    private List<String> headerNames;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getHeaderNames() {
        return headerNames;
    }

    public void setHeaderNames(List<String> headerNames) {
        this.headerNames = headerNames;
    }
}
