package com.baidu.oped.sia.boot.iplist;

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

import javax.servlet.Filter;
import javax.servlet.Servlet;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.IP_PERMISSION_PREFIX;

/**
 * Created by mason on 10/29/15.
 */
@Configuration
@ConditionalOnClass({Servlet.class, Filter.class})
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = IP_PERMISSION_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(IpListProperties.class)
public class IpListAutoConfiguration {

    @Autowired
    private IpListProperties properties;

    @Bean
    public FilterRegistrationBean ipListFilterRegistrationBean() {
        IpFilter filter = new IpFilter(buildFileWatcher());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(FilterOrder.getOrder(FilterOrder.IP_LIST));
        return registrationBean;
    }

    private FileWatcher<IpHolder> buildFileWatcher() {
        int refreshInterval = properties.getRefreshInterval();
        return new FileWatcher<>(refreshInterval,
                FileUtils.resolveConfigFile(properties.getConfigPath(), properties.getConfigFile()),
                IpHolder.class);
    }

}
