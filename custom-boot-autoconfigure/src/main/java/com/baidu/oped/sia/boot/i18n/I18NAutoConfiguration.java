package com.baidu.oped.sia.boot.i18n;

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

import java.util.Locale;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.I18N_PREFIX;

/**
 * Enable I18N support configuration
 * <p>
 * Created by mason on 10/15/15.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = I18N_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(I18nProperties.class)
public class I18NAutoConfiguration extends WebMvcConfigurerAdapter {
    private static final String defaultEncoding = "UTF-8";

    @Autowired
    private I18nProperties properties;

    @Bean
    public LocaleResolver localResolver() {
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

    private LocaleResolver buildSessionResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale(properties.getDefaultLocale()));
        return resolver;
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

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(properties.getMsgBaseName());
        messageSource.setDefaultEncoding(defaultEncoding);
        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(properties.getLocalParam());
        registry.addInterceptor(interceptor);
    }
}
