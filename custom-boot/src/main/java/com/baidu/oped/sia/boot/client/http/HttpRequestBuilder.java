package com.baidu.oped.sia.boot.client.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Http request builder.
 *
 * @author mason
 */
public class HttpRequestBuilder<T> {
    private final String endpoint;
    private final StringBuilder path;
    private HttpHeaders headers = new HttpHeaders();
    private Map<String, Object> parameters = new LinkedHashMap<>();
    private HttpEntity<T> entity;
    private HttpMethod method;

    public static <T> HttpRequestBuilder<T> get(String endpoint) {
        return new HttpRequestBuilder<>(endpoint);
    }

    private HttpRequestBuilder(String endpoint) {
        Assert.notNull(endpoint, "request endpoint must not be null.");
        this.endpoint = endpoint.trim();
        this.path = new StringBuilder("");
    }

    public HttpRequestBuilder<T> path(String path) {
        Assert.notNull(path, "Path must not be null.");
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        this.path.append(path.trim());
        return this;
    }

    public HttpRequestBuilder<T> parameter(String paramName, Object param) {
        Object paramValues = this.parameters.get(paramName);

        if (paramValues == null) {
            this.parameters.put(paramName, param);
        }

        return this;
    }

    public HttpRequestBuilder<T> removeParameter(String paramName) {
        Object paramValues = this.parameters.get(paramName);
        if (paramValues != null) {
            this.parameters.remove(paramName);
        }
        return this;
    }

    public <H> HttpRequestBuilder<T> addHeader(String headerName, H header) {
        headers.add(headerName, header.toString());
        return this;
    }

    public <H> HttpRequestBuilder header(String headerName, H header) {
        headers.set(headerName, header.toString());
        return this;
    }

    public HttpRequestBuilder<T> removeHeader(String headerName) {
        headers.remove(headerName);
        return this;
    }

    public HttpRequestBuilder<T> acceptJson() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return this;
    }

    public HttpRequestBuilder<T> acceptXml() {
        this.headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        return this;
    }

    public HttpRequestBuilder<T> entity(T entity) {
        this.entity = new HttpEntity<>(entity);
        return this;
    }

    public HttpRequestBuilder<T> method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public String getRequestUri() {
        StringBuilder builder = new StringBuilder(endpoint);
        builder.append(path);

        if (!parameters.isEmpty()) {
            builder.append("?");
            List<String> paramPairs = parameters.keySet().stream().map(param -> String.format("%s={%s}", param, param))
                    .collect(Collectors.toList());
            builder.append(StringUtils.collectionToDelimitedString(paramPairs, "&"));
        }
        return builder.toString();
    }

    public HttpEntity<T> getEntity() {
        if (this.entity == null) {
            return new HttpEntity<>(this.headers);
        }
        return new HttpEntity<>(this.entity.getBody(), this.headers);
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
