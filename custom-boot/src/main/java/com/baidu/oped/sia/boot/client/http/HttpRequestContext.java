package com.baidu.oped.sia.boot.client.http;

import com.baidu.oped.sia.boot.client.AbstractTaskContext;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

/**
 * Http request context.
 *
 * @author mason
 */
public class HttpRequestContext<T> extends AbstractTaskContext<T> {

    private final HttpRequestBuilder requestBuilder;
    private final Class<T> responseType;

    private HttpHeaders headers;

    /**
     * Construct a http request context.
     *
     * @param requestBuilder request builder
     * @param responseType   response type
     */
    public HttpRequestContext(HttpRequestBuilder requestBuilder, Class<T> responseType) {
        Assert.notNull(requestBuilder, "Request builder must not be null.");
        this.requestBuilder = requestBuilder;
        this.responseType = responseType;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public HttpRequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    public Class<T> getResponseType() {
        return responseType;
    }
}
