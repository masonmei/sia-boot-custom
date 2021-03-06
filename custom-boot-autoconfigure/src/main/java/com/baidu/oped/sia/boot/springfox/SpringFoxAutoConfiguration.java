package com.baidu.oped.sia.boot.springfox;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.SPRING_FOX_PREFIX;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

/**
 * Spring Fox AutoConfiguration.
 *
 * @author mason
 */
@Configuration
@ConditionalOnClass({Docket.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties(SpringFoxProperties.class)
@ConditionalOnProperty(prefix = SPRING_FOX_PREFIX,
        name = ENABLED,
        havingValue = "true",
        matchIfMissing = false)
@EnableSwagger2
public class SpringFoxAutoConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private SpringFoxProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final String prefixWebJarsPattern = "/webjars/**";
        if (!registry.hasMappingForPattern(prefixWebJarsPattern)) {
            registry.addResourceHandler(prefixWebJarsPattern)
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }

        final String swaggerEndpoint = "/swagger-ui.html";
        registry.addResourceHandler(swaggerEndpoint)
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
    }

    /**
     * Api Docket Bean.
     *
     * @return the docket.
     */
    @Bean
    public Docket apiDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.groupName(properties.getGroupName()).apiInfo(buildApiInfo()).select().paths(buildPaths()).build();
        return docket;
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfo(properties.getApiInfo().getTitle(), properties.getApiInfo().getDescription(),
                properties.getApiInfo().getVersion(), properties.getApiInfo().getTermsOfServiceUrl(),
                properties.getApiInfo().getContact(), properties.getApiInfo().getLicense(),
                properties.getApiInfo().getLicenseUrl());
    }

    private Predicate<String> buildPaths() {
        Set<Predicate<String>> predicates = new HashSet<>();
        for (String pattern : properties.getPatterns()) {
            Predicate<String> ant = PathSelectors.ant(pattern);
            predicates.add(ant);
        }
        return Predicates.or(predicates);
    }
}
