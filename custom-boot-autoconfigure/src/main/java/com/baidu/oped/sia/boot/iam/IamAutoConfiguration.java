package com.baidu.oped.sia.boot.iam;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.IAM_PREFIX;


import javax.servlet.http.HttpServletRequest;

import com.baidu.bce.iam.IamClient;
import com.baidu.bce.iam.IamClientConfiguration;
import com.baidu.oped.sia.boot.common.FilterOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mason on 12/2/15.
 */
@Configuration
@ConditionalOnClass({IamClient.class, IamClientConfiguration.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties(IamProperties.class)
public class IamAutoConfiguration {
    @Autowired
    private IamProperties properties;

    public IamClient iamClient() {
        if (properties.isEnabled()) {
            IamClientConfiguration configuration = new IamClientConfiguration();
            configuration.withHost(properties.getHost())
                    .withPort(properties.getPort())
                    .withUserName(properties.getUsername())
                    .withPassword(properties.getPassword())
                    .withDefaultDomain(properties.getDomain())
                    .withTokenDiscardTtl(properties.getTokenDiscardTtl())
                    .withCacheTtl(properties.getCacheTtl())
                    .withMaxCacheSize(properties.getMaxCacheSize());
            return new IamClient(configuration);
        }
        return null;
    }

    @Bean
    @ConditionalOnProperty(prefix = IAM_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
    public IamManager iamManager() {
        DefaultIamManager iamManager = new DefaultIamManager();
        iamManager.setIamClient(iamClient());
        iamManager.setServiceAccounts(properties.getServiceAccounts());
        return iamManager;
    }

    @Bean
    @ConditionalOnProperty(prefix = IAM_PREFIX, name = ENABLED, havingValue = "false", matchIfMissing = true)
    public IamManager emptyIamManager() {
        return new IamManager() {
            @Override
            public void checkUserAuth(String userId) {
            }

            @Override
            public void checkServiceAuth(String scope) {
            }

            @Override
            public void checkServiceAndUserAuth(String scope, String userId) {
            }

            @Override
            public String getUserFromIam(HttpServletRequest servletRequest) {
                return "undefined";
            }

            @Override
            public boolean isActive() {
                return false;
            }
        };
    }

    @Bean
    public IamAccessControl accessControl() {
        IamAccessControl accessControl = new IamAccessControl();
        accessControl.setIgnoredUrls(properties.getIgnores());
        return accessControl;
    }

    @Bean
    public FilterRegistrationBean iamAuthenticationFilter(IamManager iamManager, IamAccessControl accessControl) {
        IamAuthenticationFilter filter = new IamAuthenticationFilter();
        filter.setIamManager(iamManager);
        filter.setAccessControl(accessControl);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(FilterOrder.getOrder(FilterOrder.IAM));
        return registrationBean;
    }


}
