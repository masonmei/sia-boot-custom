package com.baidu.oped.sia.boot.decode;

import static com.baidu.oped.sia.boot.utils.Constrains.DECODE_URI_PREFIX;
import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;


import com.baidu.oped.sia.boot.common.FilterOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Decode Uri Auto Configuration.
 *
 * @author mason
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = DECODE_URI_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
public class DecodeUriAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DecodeUriAutoConfiguration.class);

    @Bean
    public FilterRegistrationBean iamAuthenticationFilter() {
        LOG.info("register decode uri filter");
        DecodeUriWrapperFilter filter = new DecodeUriWrapperFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(FilterOrder.getOrder(FilterOrder.DECODE));
        return registrationBean;
    }
}
