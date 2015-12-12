/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

import static com.baidu.oped.sia.boot.utils.Constrains.VALIDATE_PREFIX;


import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mason on 7/14/15.
 */
@ConfigurationProperties(prefix = VALIDATE_PREFIX)
public class ClientSourceCheckingProperties {

    private boolean enabled = false;
    private String pattern;
    private List<String> addresses = new ArrayList<>();
    private List<String> hosts = new ArrayList<>();
    private List<String> bnsNames = new ArrayList<>();
    private int refreshInterval = 5;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public List<String> getBnsNames() {
        return bnsNames;
    }

    public void setBnsNames(List<String> bnsNames) {
        this.bnsNames = bnsNames;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
