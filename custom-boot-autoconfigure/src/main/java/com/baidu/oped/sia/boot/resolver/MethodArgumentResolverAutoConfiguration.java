package com.baidu.oped.sia.boot.resolver;

import static com.baidu.oped.sia.boot.utils.Constrains.ARGS_PREFIX;
import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Method argument resolver auto configuration.
 *
 * @author mason
 */
@Configuration
@ConditionalOnProperty(prefix = ARGS_PREFIX,
                       name = ENABLED,
                       havingValue = "true",
                       matchIfMissing = false)
@ConditionalOnClass({ResolvableHandlerInterceptor.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class MethodArgumentResolverAutoConfiguration {

    /**
     * Resolvable Resolver configuration.
     *
     * @author mason
     */
    @Configuration
    @ConditionalOnWebApplication
    protected static class ResolvableResolverMvcConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new ResolvableHandlerMethodArgumentResolver(resolvableResolver()));
        }

        @Bean
        public AnnotationResolvableResolver resolvableResolver() {
            return new AnnotationResolvableResolver();
        }
    }
}
