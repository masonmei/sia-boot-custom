package com.baidu.oped.sia.boot.logheader;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.LOG_HEADER_PREFIX;

import com.baidu.oped.sia.boot.common.FilterOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Log Header Auto configuration.
 *
 * @author mason
 */
@Configuration
@ConditionalOnProperty(prefix = LOG_HEADER_PREFIX,
        name = ENABLED,
        havingValue = "true",
        matchIfMissing = false)
@EnableConfigurationProperties(LogHeaderProperties.class)
@ConditionalOnWebApplication
public class LogHeaderAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(LogHeaderAutoConfiguration.class);

    @Autowired
    private LogHeaderProperties properties;

    /**
     * Log Header registration bean filer.
     *
     * @return log header registration bean
     */
    @Bean
    public FilterRegistrationBean statisticsCollectingFilter() {
        LOG.info("register log header filter");
        LogHeaderRequestFilter filter = new LogHeaderRequestFilter(properties.getHeaderNames());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(FilterOrder.getOrder(FilterOrder.LOG_HEADER));
        return registrationBean;
    }
}
