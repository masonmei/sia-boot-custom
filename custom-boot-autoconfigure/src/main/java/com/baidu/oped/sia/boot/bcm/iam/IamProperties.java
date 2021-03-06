package com.baidu.oped.sia.boot.bcm.iam;

import static com.baidu.oped.sia.boot.utils.Constrains.IAM_PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Iam configuration properties.
 *
 * @author mason
 */
@ConfigurationProperties(prefix = IAM_PREFIX)
public class IamProperties {
    public static final boolean NOT_ENABLED = false;

    private boolean enabled = NOT_ENABLED;
    private String host;
    private String port;
    private String protocol = "http";
    private String username;
    private String password;
    private String domain = "default";
    private String projectId;
    private int tokenDiscardTtl = 30;
    private int maxCacheSize = 10000;
    private int cacheTtl = 300;
    private List<String> ignores = new ArrayList<>();
    private List<ServiceAccount> serviceAccounts = new ArrayList<>();

    public int getCacheTtl() {
        return cacheTtl;
    }

    public void setCacheTtl(int cacheTtl) {
        this.cacheTtl = cacheTtl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getIgnores() {
        return ignores;
    }

    public void setIgnores(List<String> ignores) {
        this.ignores = ignores;
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public List<ServiceAccount> getServiceAccounts() {
        return serviceAccounts;
    }

    public void setServiceAccounts(List<ServiceAccount> serviceAccounts) {
        this.serviceAccounts = serviceAccounts;
    }

    public int getTokenDiscardTtl() {
        return tokenDiscardTtl;
    }

    public void setTokenDiscardTtl(int tokenDiscardTtl) {
        this.tokenDiscardTtl = tokenDiscardTtl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Service Account.
     *
     * @author mason
     */
    public static class ServiceAccount {
        private String userId;
        private String scope;

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
