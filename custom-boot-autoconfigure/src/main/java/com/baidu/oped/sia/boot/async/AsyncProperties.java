package com.baidu.oped.sia.boot.async;

import com.baidu.oped.sia.boot.utils.Constrains;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mason on 11/5/15.
 */
@ConfigurationProperties(prefix = Constrains.ASYNC_PREFIX)
public class AsyncProperties {
    private boolean enabled = false;
    private Executor executor;

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    static class Executor {

        private String threadNamePrefix;
        private String threadGroupName;
        private int poolSize;

        public int getPoolSize() {
            return poolSize;
        }

        public void setPoolSize(int poolSize) {
            this.poolSize = poolSize;
        }

        public String getThreadGroupName() {
            return threadGroupName;
        }

        public void setThreadGroupName(String threadGroupName) {
            this.threadGroupName = threadGroupName;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }
}
