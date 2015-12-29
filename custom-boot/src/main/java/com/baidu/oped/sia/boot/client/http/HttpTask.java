package com.baidu.oped.sia.boot.client.http;

import com.baidu.oped.sia.boot.client.AbstractTask;
import com.baidu.oped.sia.boot.exception.internal.RetryableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Http invoking task.
 *
 * @author mason
 */
public class HttpTask<T> extends AbstractTask<T> {
    private static final Logger LOG = LoggerFactory.getLogger(HttpTask.class);

    private RestTemplate restTemplate;

    public HttpTask(HttpRequestContext<T> context) {
        super(context);
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "Rest template must not be null.");
        this.restTemplate = restTemplate;
    }

    @Override
    protected T realExecute() throws RetryableException {
        HttpRequestContext<T> context = (HttpRequestContext<T>) this.context;
        HttpRequestBuilder builder = context.getRequestBuilder();

        try {
            ResponseEntity<T> exchange = restTemplate
                    .exchange(builder.getRequestUri(), builder.getMethod(), builder.getEntity(),
                            context.getResponseType(), builder.getParameters());
            context.setHeaders(exchange.getHeaders());
            return exchange.getBody();
        } catch (RestClientException exception) {
            if (exception instanceof HttpClientErrorException) {
                HttpClientErrorException clientErrorException = (HttpClientErrorException) exception;

                if (clientErrorException.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
                    throw new RetryableException();
                }

                LOG.warn("remote request failed with status: {}, message: {}", clientErrorException.getStatusCode(),
                        clientErrorException.getResponseBodyAsString());
                return null;
            } else {
                throw new RetryableException();
            }
        }
    }
}
