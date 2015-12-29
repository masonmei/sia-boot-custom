package com.baidu.oped.sia.boot.client.http;

import com.baidu.oped.sia.boot.client.AbstractClient;
import com.baidu.oped.sia.boot.client.Context;
import com.baidu.oped.sia.boot.client.Task;
import com.baidu.oped.sia.boot.exception.SystemException;
import com.baidu.oped.sia.boot.exception.internal.RetryableException;
import com.baidu.oped.sia.boot.exception.internal.external.ExternalInvocationException;

import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Http service invoking client.
 *
 * @author mason
 */
public class HttpClient extends AbstractClient {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

    private final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
            = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
    private final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

    public HttpClient() {
        super();
    }

    public HttpClient(int maxRetries, int maxRetryInterval) {
        super(maxRetries, maxRetryInterval);
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setTimeout(int timeoutInMillis) {
        clientHttpRequestFactory.setConnectionRequestTimeout(timeoutInMillis);
    }

    @Override
    protected <T> void handleException(Context<T> context, RetryableException exception) {
        HttpRequestContext<T> requestContext = (HttpRequestContext<T>) context;
        LOG.debug("{} times invoking http service with url: {} failed", requestContext.getExecutions(),
                requestContext.getRequestBuilder().getRequestUri());
        if (meetMaxRetry(context)) {
            context.markComplete();
            throw new ExternalInvocationException();
        }
    }

    @Override
    protected <T> void preProcessTask(Task<T> task) throws SystemException {
        HttpTask<T> httpTask = (HttpTask<T>) task;
        httpTask.setRestTemplate(restTemplate);
    }


}
