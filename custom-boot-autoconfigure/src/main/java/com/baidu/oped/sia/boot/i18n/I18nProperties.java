package com.baidu.oped.sia.boot.i18n;

import static com.baidu.oped.sia.boot.utils.Constrains.I18N_PREFIX;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * I18n Auto configuration properties.
 *
 * @author mason
 */
@ConfigurationProperties(prefix = I18N_PREFIX)
public class I18nProperties {
    private static final List<String> DEFAULT_I18N_MSG_BASE_NAME = Arrays.asList("classpath:/i18n/messages");
    private static final String DEFAULT_LOCALE_PARAM = "locale";
    private static final String DEFAULT_LOCALE = "en";

    private boolean enabled = false;
    private List<String> msgBaseNames = DEFAULT_I18N_MSG_BASE_NAME;
    private String localParam = DEFAULT_LOCALE_PARAM;
    private String defaultLocale = DEFAULT_LOCALE;

    private ResolverType resolverType = ResolverType.COOKIE;

    private CookieConfig cookie = new CookieConfig();

    public CookieConfig getCookie() {
        return cookie;
    }

    public void setCookie(CookieConfig cookie) {
        this.cookie = cookie;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getLocalParam() {
        return localParam;
    }

    public void setLocalParam(String localParam) {
        this.localParam = localParam;
    }

    public List<String> getMsgBaseNames() {
        return msgBaseNames;
    }

    public void setMsgBaseNames(List<String> msgBaseNames) {
        this.msgBaseNames = msgBaseNames;
    }

    public ResolverType getResolverType() {
        return resolverType;
    }

    public void setResolverType(ResolverType resolverType) {
        this.resolverType = resolverType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Created by mason on 11/17/15.
     */
    public static class CookieConfig {
        private static final String I18N_COOKIE_NAME = "locale_cookie";
        private static final Integer MAX_COOKIE_AGE_IN_SECOND = 3600;

        private String name = I18N_COOKIE_NAME;
        private int maxAge = MAX_COOKIE_AGE_IN_SECOND;
        private String domain = "";

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public int getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(int maxAge) {
            this.maxAge = maxAge;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("name", name).add("maxAge", maxAge).add("domain", domain)
                    .toString();
        }
    }
}
