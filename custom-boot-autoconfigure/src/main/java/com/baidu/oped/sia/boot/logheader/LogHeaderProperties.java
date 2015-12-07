/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.logheader;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static com.baidu.oped.sia.boot.utils.Constrains.LOG_HEADER_PREFIX;

/**
 * Created by meidongxu on 7/2/15.
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
