package com.baidu.oped.sia.boot.dualssl;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.baidu.oped.sia.boot.utils.Constrains.SSL_DUAL_PREFIX;

/**
 * Created by mason on 11/19/15.
 */
@ConfigurationProperties(prefix = SSL_DUAL_PREFIX)
public class DualSslProperties {

    private int httpPort;
    private boolean redirectSsl;

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public boolean isRedirectSsl() {
        return redirectSsl;
    }

    public void setRedirectSsl(boolean redirectSsl) {
        this.redirectSsl = redirectSsl;
    }
}
