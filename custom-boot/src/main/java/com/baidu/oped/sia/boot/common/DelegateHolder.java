package com.baidu.oped.sia.boot.common;

import org.springframework.util.Assert;

/**
 * Delegate Holder.
 *
 * @author mason
 */
public class DelegateHolder<T> {
    private T context;

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        Assert.notNull(context, "Context must not be null.");
        this.context = context;
    }
}
