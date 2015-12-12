package com.baidu.oped.sia.boot.limit;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.LIMIT_PREFIX;


import javax.servlet.Filter;
import javax.servlet.Servlet;

import com.baidu.oped.sia.boot.common.FileWatcher;
import com.baidu.oped.sia.boot.common.FilterOrder;
import com.baidu.oped.sia.boot.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mason on 10/29/15.
 */
@Configuration
@ConditionalOnClass({Servlet.class, Filter.class})
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = LIMIT_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(LimiterProperties.class)
public class LimiterAutoConfiguration {

    @Autowired
    private LimiterProperties properties;

    @Bean
    public FilterRegistrationBean ipListFilterRegistrationBean() {
        SimpleLimiterFilter filter = new SimpleLimiterFilter(buildFileWatcher(), buildLimiterConfig());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(FilterOrder.getOrder(FilterOrder.IP_LIMIT));

        return registrationBean;
    }

    private LimiterConfig buildLimiterConfig() {
        LimiterConfig limiterConfig = new LimiterConfig();
        limiterConfig.setMaxRequestsPerPeriod(properties.getMaxRequestsPerPeriod());
        limiterConfig.setPeriodInMs(properties.getPeriod() * 1000);
        limiterConfig.setBandTimeInMs(properties.getBandTime() * 1000);
        return limiterConfig;
    }

    private FileWatcher<WhiteListIpHolder> buildFileWatcher() {
        int refreshInterval = properties.getRefreshInterval();
        return new FileWatcher<>(refreshInterval,
                FileUtils.resolveConfigFile(properties.getConfigPath(), properties.getConfigFile()),
                WhiteListIpHolder.class);
    }

}
