package com.baidu.oped.sia.boot.client.thrift;

import com.baidu.oped.sia.boot.client.AbstractTask;
import com.baidu.oped.sia.boot.client.Context;
import com.baidu.oped.sia.boot.exception.internal.RetryableException;

import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Thrift Task for operate data.
 *
 * @author mason
 */
public class ThriftTask<T> extends AbstractTask<T> {
    private static final Logger LOG = LoggerFactory.getLogger(ThriftTask.class);

    private TServiceClient serviceClient;

    public ThriftTask(Context<T> context) {
        super(context);
    }

    public void setServiceClient(TServiceClient serviceClient) {
        Assert.notNull(serviceClient, "Service Client must not be null.");
        this.serviceClient = serviceClient;

    }

    @Override
    protected T realExecute() throws RetryableException {
        ThriftTaskContext<T>  taskContext = (ThriftTaskContext<T>) this.context;
        return null;
    }
}
