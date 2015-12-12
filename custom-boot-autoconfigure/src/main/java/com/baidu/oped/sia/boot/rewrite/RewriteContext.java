package com.baidu.oped.sia.boot.rewrite;

import static com.baidu.oped.sia.boot.utils.Constrains.REWRITE_PREFIX;


import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
    private List<RewriteEntry> rewriteEntries;

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

    public List<RewriteEntry> getRewriteEntries() {
        return rewriteEntries;
    }

    public void setRewriteEntries(List<RewriteEntry> rewriteEntries) {
        this.rewriteEntries = rewriteEntries;
    }

    /**
     * Rewrite configuration.
     *
     * @author mason
     */
    public static class RewriteEntry {
        private String pattern;
        private String rewrite;

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
}
