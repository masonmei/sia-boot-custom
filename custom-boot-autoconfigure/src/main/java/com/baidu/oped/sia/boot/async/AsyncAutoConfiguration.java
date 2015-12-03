package com.baidu.oped.sia.boot.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static com.baidu.oped.sia.boot.utils.Constrains.ASYNC_PREFIX;
import static com.baidu.oped.sia.boot.utils.Constrains.ENABLED;

/**
 * Created by mason on 11/5/15.
 */
@Configuration
@ConditionalOnWebApplication
@EnableAsync
@ConditionalOnProperty(prefix = ASYNC_PREFIX, name = ENABLED, havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(AsyncProperties.class)
public class AsyncAutoConfiguration implements AsyncConfigurer {
    public static final Logger LOG = LoggerFactory.getLogger(AsyncAutoConfiguration.class);

    @Autowired
    private AsyncProperties properties;

    @Override
    public Executor getAsyncExecutor() {
        LOG.info("initial the async executor.");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(properties.getExecutor().getThreadNamePrefix());
        executor.setThreadGroupName(properties.getExecutor().getThreadGroupName());
        executor.setCorePoolSize(properties.getExecutor().getPoolSize());
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}