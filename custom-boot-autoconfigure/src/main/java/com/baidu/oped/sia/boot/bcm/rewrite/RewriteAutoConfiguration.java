package com.baidu.oped.sia.boot.bcm.rewrite;

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
 * Rewrite Auto configuration.
 *
 * @author mason
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

    /**
     * Define rewrite filter.
     *
     * @param parameterResolver param resolver
     * @return Rewrite Filter Registration Bean
     */
    @Bean
    public FilterRegistrationBean rewriteFiler(AgentUriRewriteParameterResolver parameterResolver) {
        AgentUriRewriteFiler rewriteFiler = new AgentUriRewriteFiler();
        rewriteFiler.setParameterResolver(parameterResolver);
        rewriteFiler.setRewriteContext(rewriteContext);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(rewriteFiler);
        return registrationBean;
    }

    /**
     * Param resolver.
     *
     * @return param resolver
     */
    @Bean
    @ConditionalOnMissingBean
    public AgentUriRewriteParameterResolver uriRewriteParameterResolver() {
        return new AgentUriRewriteParameterResolver() {
            @Override
            public Map<String, String> resolve(HttpServletRequest request) {
                return Collections.emptyMap();
            }
        };
    }
}
