package com.baidu.oped.sia.boot.limit;

/**
 * Created by mason on 11/10/15.
 */
public class LimiterConfig {
    private int maxRequestsPerPeriod;
    private int periodInMs;
    private int bandTimeInMs;

    public int getMaxRequestsPerPeriod() {
        return maxRequestsPerPeriod;
    }

    public void setMaxRequestsPerPeriod(int maxRequestsPerPeriod) {
        this.maxRequestsPerPeriod = maxRequestsPerPeriod;
    }

    public int getPeriodInMs() {
        return periodInMs;
    }

    public void setPeriodInMs(int periodInMs) {
        this.periodInMs = periodInMs;
    }

    public int getBandTimeInMs() {
        return bandTimeInMs;
    }

    public void setBandTimeInMs(int bandTimeInMs) {
        this.bandTimeInMs = bandTimeInMs;
    }
}
