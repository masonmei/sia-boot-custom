package com.baidu.oped.sia.boot.accesslog;

import static com.baidu.oped.sia.boot.utils.Constrains.ACCESS_LOG_PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Access log properties.
 *
 * @author mason
 */
@ConfigurationProperties(prefix = ACCESS_LOG_PREFIX)
public class AccessLogProperties {
    private boolean enabled = true;
    private String configFile = "logback-access.xml";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
}
