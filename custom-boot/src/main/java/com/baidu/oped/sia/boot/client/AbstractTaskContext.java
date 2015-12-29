package com.baidu.oped.sia.boot.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract Task Context.
 *
 * @author mason
 */
public abstract class AbstractTaskContext<T> implements Context<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractTaskContext.class);

    private final AtomicInteger executions = new AtomicInteger(0);
    private final AtomicBoolean completed = new AtomicBoolean(false);
    private T result;

    public AbstractTaskContext() {
    }

    @Override
    public String contextInfo() {
        return toString();
    }

    @Override
    public int getExecutions() {
        return executions.get();
    }

    @Override
    public boolean isComplete() {
        return completed.get();
    }

    @Override
    public void markComplete() {
        completed.set(true);
    }

    @Override
    public void markExecution() {
        int executionTimes = this.executions.getAndIncrement();
        LOG.debug("mark task execution, task: {} already context {} times", contextInfo(), executionTimes);
    }

    @Override
    public T result() {
        return this.result;
    }

    @Override
    public void setResult(T result) {
        this.result = result;
    }

}
