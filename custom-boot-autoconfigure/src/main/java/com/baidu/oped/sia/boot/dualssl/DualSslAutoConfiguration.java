package com.baidu.oped.sia.boot.dualssl;

import io.undertow.Undertow;
import org.apache.catalina.connector.Connector;
import org.eclipse.jetty.server.NetworkTrafficServerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;
import static com.baidu.oped.sia.boot.utils.Constrains.SSL_DUAL_PREFIX;

/**
 * Created by mason on 11/19/15.
 */
@Configuration
@ConditionalOnProperty(prefix = SSL_DUAL_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(DualSslProperties.class)
public class DualSslAutoConfiguration {

    @Autowired
    private DualSslProperties properties;

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;

                    Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
                    connector.setPort(properties.getHttpPort());
                    factory.addAdditionalTomcatConnectors(connector);
                }

                if (container instanceof JettyEmbeddedServletContainerFactory) {
                    JettyEmbeddedServletContainerFactory factory = (JettyEmbeddedServletContainerFactory) container;
                    factory.addServerCustomizers(new JettyServerCustomizer() {
                        @Override
                        public void customize(org.eclipse.jetty.server.Server server) {
                            NetworkTrafficServerConnector connector = new NetworkTrafficServerConnector(server);
                            connector.setPort(properties.getHttpPort());
                            server.addConnector(connector);
                        }
                    });
                }

                if(container instanceof UndertowEmbeddedServletContainerFactory) {
                    UndertowEmbeddedServletContainerFactory factory = (UndertowEmbeddedServletContainerFactory) container;
                    factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
                        @Override
                        public void customize(Undertow.Builder builder) {
                            builder.addHttpListener(properties.getHttpPort(), "0.0.0.0");
                        }
                    });
                }
            }
        };
    }

    @Configuration
    @ConditionalOnProperty(prefix = SSL_DUAL_PREFIX, name = "redirect-ssl", havingValue = "true", matchIfMissing = false)
    public class WebSecurityAutoRedirectConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }
}
