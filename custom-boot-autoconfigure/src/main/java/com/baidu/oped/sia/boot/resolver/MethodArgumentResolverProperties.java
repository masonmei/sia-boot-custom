package com.baidu.oped.sia.boot.resolver;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.baidu.oped.sia.boot.utils.Constrains.ARGS_PREFIX;

/**
 * Created by mason on 11/18/15.
 */
@ConfigurationProperties(prefix = ARGS_PREFIX)
public class MethodArgumentResolverProperties {
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
