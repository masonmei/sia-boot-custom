package com.baidu.oped.sia.boot.rewrite;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.REWRITE_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by mason on 12/2/15.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = REWRITE_PREFIX,
                       name = ENABLED,
                       havingValue = "true",
                       matchIfMissing = false)
@EnableConfigurationProperties(RewriteContext.class)
public class RewriteAutoConfiguration {
    @Autowired
    private RewriteContext rewriteContext;

    @Bean
    public FilterRegistrationBean rewriteFiler(UriRewriteParameterResolver parameterResolver) {
        UriRewriteFiler rewriteFiler = new UriRewriteFiler();
        rewriteFiler.setParameterResolver(parameterResolver);
        rewriteFiler.setRewriteContext(rewriteContext);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(rewriteFiler);
        return registrationBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public UriRewriteParameterResolver uriRewriteParameterResolver() {
        return new UriRewriteParameterResolver() {
            @Override
            public Map<String, String> resolve(HttpServletRequest request) {
                return Collections.emptyMap();
            }
        };
    }
}
