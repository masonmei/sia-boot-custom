package com.baidu.oped.sia.boot.accesslog;

import static com.baidu.oped.sia.boot.utils.Constrains.ACCESS_LOG_PREFIX;
import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;

import com.baidu.oped.sia.boot.accesslog.jetty.JettyAccessLogCustomizer;
import com.baidu.oped.sia.boot.accesslog.undertow.LogbackAccessLogReceiver;
import com.baidu.oped.sia.boot.accesslog.undertow.LogbackHandlerWrapper;
import com.baidu.oped.sia.boot.utils.FileUtils;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import io.undertow.servlet.api.DeploymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Access Log Auto Configuration.
 *
 * @author mason
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = ACCESS_LOG_PREFIX,
        name = ENABLED,
        havingValue = "true",
        matchIfMissing = false)
@EnableConfigurationProperties(AccessLogProperties.class)
public class AccessLogAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(AccessLogAutoConfiguration.class);

    @Autowired
    private AccessLogProperties properties;

    /**
     * Custom the container with access log enabled.
     *
     * @return container customizer
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        LOG.info("enable access log.");
        final File file = FileUtils.resolveFile(properties.getConfigFile());

        return container -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
                LogbackValve requestLog = new LogbackValve();
                requestLog.setFilename(file.getAbsolutePath());
                factory.getValves().add(requestLog);
            }

            if (container instanceof JettyEmbeddedServletContainerFactory) {
                JettyEmbeddedServletContainerFactory factory = (JettyEmbeddedServletContainerFactory) container;
                factory.addServerCustomizers(new JettyAccessLogCustomizer(file));
            }

            if (container instanceof UndertowEmbeddedServletContainerFactory) {
                UndertowEmbeddedServletContainerFactory factory =
                        (UndertowEmbeddedServletContainerFactory) container;
                factory.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {
                    @Override
                    public void customize(DeploymentInfo deploymentInfo) {
                        deploymentInfo.addInitialHandlerChainWrapper(handler -> {
                            LogbackAccessLogReceiver accessLogReceiver = new LogbackAccessLogReceiver();
                            accessLogReceiver.setFileName(file.getAbsolutePath());
                            if (!accessLogReceiver.isStarted()) {
                                accessLogReceiver.start();
                            }
                            return new LogbackHandlerWrapper(handler, accessLogReceiver);
                        });

                    }
                });
            }
        };
    }

    /**
     * Tee filter.
     *
     * @return tee filter
     */
    @Bean
    public FilterRegistrationBean teeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new TeeFilter());
        return registrationBean;
    }
}
