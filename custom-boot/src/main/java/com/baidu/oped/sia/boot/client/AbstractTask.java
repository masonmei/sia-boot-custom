package com.baidu.oped.sia.boot.client;

import com.baidu.oped.sia.boot.exception.RetryableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Abstract Task to be execute.
 *
 * @author mason
 */
public abstract class AbstractTask<T> implements Task<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractTask.class);

    private final Context<T> context;

    public AbstractTask(Context<T> context) {
        Assert.notNull(context, "TaskContext must not be null.");
        this.context = context;
    }

    @Override
    public Context<T> context() {
        return context;
    }

    @Override
    public void execute() {
        LOG.debug("start to execute task: {}", context.contextInfo());
        context.markExecution();

        try {
            T result = realExecute();
            context.setResult(result);
            context.markComplete();
        } catch (RetryableException e) {
            LOG.warn("retryable exception occurred: {}", e.getMessage());
            throw e;
        }
    }

    protected abstract T realExecute() throws RetryableException;

}
