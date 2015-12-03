package com.baidu.oped.sia.boot.iplist;

import com.baidu.oped.sia.boot.utils.Constrains;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mason on 10/29/15.
 */
@ConfigurationProperties(prefix = Constrains.IP_PERMISSION_PREFIX)
public class IpListProperties {
    private boolean enabled = false;
    private String configPath;
    private String configFile = "iplist.yaml";
    private int refreshInterval = 10;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
