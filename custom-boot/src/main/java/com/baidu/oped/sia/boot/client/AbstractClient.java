package com.baidu.oped.sia.boot.client;

import static com.baidu.oped.sia.boot.utils.Constrains.ClientConstrains.MAX_RETRY_INTERVAL;
import static com.baidu.oped.sia.boot.utils.Constrains.ClientConstrains.RETRIES;

import com.baidu.oped.sia.boot.exception.internal.RetryableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Execute task with the client.
 *
 * @author mason
 */
public abstract class AbstractClient implements Client {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractClient.class);
    private final int maxRetries;
    private final int maxRetryInterval;

    public AbstractClient() {
        this(RETRIES, MAX_RETRY_INTERVAL);
    }

    /**
     * Construct a client with max retries and max retry interval.
     *
     * @param maxRetries       max retries
     * @param maxRetryInterval max retry interval in millis
     */
    public AbstractClient(int maxRetries, int maxRetryInterval) {
        Assert.state(maxRetries > 0, "Max retries must be positive.");
        Assert.state(maxRetryInterval > 0, "Max retry interval must be positive.");
        this.maxRetries = maxRetries;
        this.maxRetryInterval = maxRetryInterval;
    }

    @Override
    public <T> void execute(Task<T> task) {
        Context<T> context = task.context();
        while (!context.isComplete() && context.getExecutions() < maxRetries) {
            try {
                preProcessTask(task);
                task.execute();
            } catch (RetryableException re) {
                handleException(context, re);
            }
            randomSleep();
        }
    }

    protected abstract <T> void handleException(Context<T> context, RetryableException exception);

    protected <T> boolean meetMaxRetry(Context<T> context) {
        return context.getExecutions() >= maxRetries;
    }

    protected <T> void preProcessTask(Task<T> task) {
    }

    private void randomSleep() {
        long randomSleep = Math.round(Math.random() * maxRetryInterval);
        try {
            Thread.sleep(randomSleep);
        } catch (InterruptedException e) {
            LOG.warn("random sleep failed.");
        }
    }
}
