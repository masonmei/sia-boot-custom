package com.baidu.oped.sia.boot.client.http;

import com.baidu.oped.sia.boot.client.AbstractTask;
import com.baidu.oped.sia.boot.client.Context;
import com.baidu.oped.sia.boot.exception.RetryableException;

/**
 * Http invoking task.
 *
 * @author mason
 */
public class HttpTask<T> extends AbstractTask<T> {

    public HttpTask(HttpRequestContext context) {
        super(context);
    }

    @Override
    public Context context() {

        return null;
    }

    @Override
    protected T realExecute() throws RetryableException {
        return null;
    }
}
