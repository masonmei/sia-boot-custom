package com.baidu.oped.sia.boot.dualssl;

import static com.baidu.oped.sia.boot.utils.Constrains.SSL_DUAL_PREFIX;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Dual ssl properties.
 *
 * @author mason
 */
@ConfigurationProperties(prefix = SSL_DUAL_PREFIX)
public class DualSslProperties {

    private int httpPort;
    private boolean redirectSsl;

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public boolean isRedirectSsl() {
        return redirectSsl;
    }

    public void setRedirectSsl(boolean redirectSsl) {
        this.redirectSsl = redirectSsl;
    }
}
