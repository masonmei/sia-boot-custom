package com.baidu.oped.sia.boot.limit;

import com.baidu.oped.sia.boot.utils.Constrains;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mason on 10/29/15.
 */
@ConfigurationProperties(prefix = Constrains.LIMIT_PREFIX)
public class LimiterProperties {
    private boolean enabled = false;
    private String configPath;
    private String configFile = "whiteList.yaml";
    private int refreshInterval = 10;
    private int maxRequestsPerPeriod = 100;
    private int period = 10;
    private int bandTime = 60;

    public int getBandTime() {
        return bandTime;
    }

    public void setBandTime(int bandTime) {
        this.bandTime = bandTime;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public int getMaxRequestsPerPeriod() {
        return maxRequestsPerPeriod;
    }

    public void setMaxRequestsPerPeriod(int maxRequestsPerPeriod) {
        this.maxRequestsPerPeriod = maxRequestsPerPeriod;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
