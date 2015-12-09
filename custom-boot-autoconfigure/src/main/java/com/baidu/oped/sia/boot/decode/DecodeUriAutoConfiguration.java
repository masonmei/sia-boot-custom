package com.baidu.oped.sia.boot.decode;

import com.baidu.oped.sia.boot.common.FilterOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.baidu.oped.sia.boot.utils.Constrains.DECODE_URI_PREFIX;
import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;

/**
 * Created by mason on 12/9/15.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = DECODE_URI_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
public class DecodeUriAutoConfiguration {

    @Bean
    public FilterRegistrationBean iamAuthenticationFilter() {
        DecodeUriWrapperFilter filter = new DecodeUriWrapperFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(FilterOrder.getOrder(FilterOrder.DECODE));
        return registrationBean;
    }
}
