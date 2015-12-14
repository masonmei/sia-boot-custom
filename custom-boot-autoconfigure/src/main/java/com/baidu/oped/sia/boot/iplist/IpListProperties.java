package com.baidu.oped.sia.boot.iplist;

import com.baidu.oped.sia.boot.utils.Constrains;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Ip List Configuration Properties.
 *
 * @author mason
 */
@ConfigurationProperties(prefix = Constrains.IP_PERMISSION_PREFIX)
public class IpListProperties {
    private boolean enabled = false;
    private String configPath;
    private String configFile = "iplist.yaml";
    private int refreshInterval = 10;

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
