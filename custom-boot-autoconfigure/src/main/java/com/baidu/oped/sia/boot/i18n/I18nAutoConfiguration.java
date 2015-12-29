package com.baidu.oped.sia.boot.i18n;

import static com.baidu.oped.sia.boot.utils.Constrains.CUSTOM_BOOT_RESOURCE_BUNDLE;
import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.I18N_PREFIX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Enable I18N support configuration.
 *
 * @author mason
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = I18N_PREFIX,
        name = ENABLED,
        havingValue = "true",
        matchIfMissing = false)
@EnableConfigurationProperties(I18nProperties.class)
public class I18nAutoConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(I18nAutoConfiguration.class);

    private static final String DEFAULT_ENCODING = "UTF-8";

    @Autowired
    private I18nProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOG.info("add i18n LocaleChangeInterceptor.");
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(properties.getLocalParam());
        registry.addInterceptor(interceptor);
    }

    /**
     * Define locale resolver bean.
     *
     * @return the locale resolver bean
     */
    @Bean
    public LocaleResolver localeResolver() {
        LOG.info("enable i18n resolver. Resolver type: {}", properties.getResolverType());
        LocaleResolver resolver = null;
        switch (properties.getResolverType()) {
            case COOKIE:
                resolver = buildCookieResolver(properties.getCookie());
                break;
            case SESSION:
                resolver = buildSessionResolver();
                break;
            default:
        }
        if (resolver == null) {
            resolver = buildCookieResolver(properties.getCookie());
        }

        return resolver;
    }

    /**
     * Define message source bean.
     *
     * @return message source
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        List<String> msgBaseNames = new ArrayList<>();
        msgBaseNames.addAll(properties.getMsgBaseNames());
        if (!msgBaseNames.contains(CUSTOM_BOOT_RESOURCE_BUNDLE)) {
            msgBaseNames.add(CUSTOM_BOOT_RESOURCE_BUNDLE);
        }
        messageSource.setBasenames(msgBaseNames.toArray(new String[msgBaseNames.size()]));
        messageSource.setDefaultEncoding(DEFAULT_ENCODING);
        return messageSource;
    }

    private LocaleResolver buildCookieResolver(I18nProperties.CookieConfig config) {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieMaxAge(config.getMaxAge());
        localeResolver.setCookieName(config.getName());
        if (!StringUtils.isEmpty(config.getDomain())) {
            localeResolver.setCookieDomain(config.getDomain());
        }

        localeResolver.setDefaultLocale(new Locale(properties.getDefaultLocale()));
        return localeResolver;
    }

    private LocaleResolver buildSessionResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale(properties.getDefaultLocale()));
        return resolver;
    }
}
