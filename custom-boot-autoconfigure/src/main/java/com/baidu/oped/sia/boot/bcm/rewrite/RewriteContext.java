package com.baidu.oped.sia.boot.bcm.rewrite;

import static com.baidu.oped.sia.boot.utils.Constrains.REWRITE_PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Rewrite Context.
 *
 * @author mason
 */
@Component
@ConfigurationProperties(prefix = REWRITE_PREFIX)
public class RewriteContext {
    private boolean enabled = false;
    private boolean resolveParam = true;
    private String pattern;
    private String rewrite;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isResolveParam() {
        return resolveParam;
    }

    public void setResolveParam(boolean resolveParam) {
        this.resolveParam = resolveParam;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getRewrite() {
        return rewrite;
    }

    public void setRewrite(String rewrite) {
        this.rewrite = rewrite;
    }
}
