package com.baidu.oped.sia.boot.resolver;

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

import static com.baidu.oped.sia.boot.utils.Constrains.ARGS_PREFIX;
import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;

/**
 * Created by mason on 11/18/15.
 */
@Configuration
@ConditionalOnProperty(prefix = ARGS_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@ConditionalOnClass({ResolvableHandlerInterceptor.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class MethodArgumentResolverAutoConfiguration {

    @Configuration
    @ConditionalOnWebApplication
    protected static class ResolvableResolverMvcConfiguration extends WebMvcConfigurerAdapter {

        @Bean
        public AnnotationResolvableResolver resolvableResolver() {
            return new AnnotationResolvableResolver();
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new ResolvableHandlerMethodArgumentResolver(resolvableResolver()));
        }
    }
}
