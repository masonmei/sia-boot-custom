package com.baidu.oped.sia.boot.client.thrift;

import com.baidu.oped.sia.boot.client.AbstractClient;
import com.baidu.oped.sia.boot.client.Context;
import com.baidu.oped.sia.boot.client.Task;
import com.baidu.oped.sia.boot.exception.internal.RetryableException;

import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thrift client is the base client for all the thrift requests.
 *
 * @author mason
 **/
public class ThriftClient<TC extends TServiceClient> extends AbstractClient {
    private static final Logger LOG = LoggerFactory.getLogger(ThriftClient.class);

    private final TC client;

    public ThriftClient(TC client) {
        this.client = client;
    }

    @Override
    protected <T> void preProcessTask(Task<T> task) {
        super.preProcessTask(task);

    }

    @Override
    protected <T> void handleException(Context<T> context, RetryableException exception) {

    }
}
