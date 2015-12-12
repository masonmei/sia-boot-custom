package com.baidu.oped.sia.boot.profiling;

import com.baidu.oped.sia.boot.utils.Constrains;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mason on 11/5/15.
 */
@ConfigurationProperties(prefix = Constrains.PROFILE_PREFIX)
public class ProfilingProperties {

    private boolean enabled = false;
    private long warningThreshold = 1000;
    private long logFrequency = 100;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getWarningThreshold() {
        return warningThreshold;
    }

    public void setWarningThreshold(long warningThreshold) {
        this.warningThreshold = warningThreshold;
    }

    public long getLogFrequency() {
        return logFrequency;
    }

    public void setLogFrequency(long logFrequency) {
        this.logFrequency = logFrequency;
    }
}
