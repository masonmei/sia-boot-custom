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
public class HttpRequestBuilder {
    private final String endpoint;
    private final StringBuilder path;
    private HttpHeaders headers = new HttpHeaders();
    private Map<String, Object> parameters = new LinkedHashMap<>();
    private HttpEntity<?> entity;
    private HttpMethod method;

    private HttpRequestBuilder(String endpoint) {
        Assert.notNull(endpoint, "request endpoint must not be null.");
        this.endpoint = endpoint.trim();
        this.path = new StringBuilder("");
    }

    public static HttpRequestBuilder get(String endpoint) {
        return new HttpRequestBuilder(endpoint);
    }

    /**
     * Set the accept to json.
     *
     * @return current builder
     */
    public HttpRequestBuilder acceptJson() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return this;
    }

    /**
     * Set the accept to xml.
     *
     * @return current builder
     */
    public HttpRequestBuilder acceptXml() {
        this.headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        return this;
    }

    /**
     * Add a header to builder.
     *
     * @param headerName header name
     * @param header     header value
     * @param <H>        header type
     * @return current builder
     */
    public <H> HttpRequestBuilder addHeader(String headerName, H header) {
        headers.add(headerName, header.toString());
        return this;
    }

    /**
     * set entity when post or put data.
     *
     * @param entity context to post
     * @param <T>    entity type
     * @return current builder
     */
    public <T> HttpRequestBuilder entity(T entity) {
        this.entity = new HttpEntity<>(entity);
        return this;
    }

    /**
     * Get the request entity with headers set.
     *
     * @return http entity
     */
    public HttpEntity getEntity() {
        if (this.entity == null) {
            return new HttpEntity<>(this.headers);
        }
        return new HttpEntity<>(this.entity.getBody(), this.headers);
    }

    /**
     * Get http headers.
     *
     * @return headers
     */
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    /**
     * Get the request method.
     *
     * @return request method.
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Get the request params.
     *
     * @return request params map
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Get the request uri with params.
     *
     * @return request uri with params
     */
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

    /**
     * Set header or replace header with given name.
     *
     * @param headerName header name
     * @param header     header value
     * @param <H>        header type
     * @return current builder
     */
    public <H> HttpRequestBuilder header(String headerName, H header) {
        headers.set(headerName, header.toString());
        return this;
    }

    /**
     * set the request method.
     *
     * @param method request method.
     * @return current builder
     */
    public HttpRequestBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    /**
     * Add request parameter to request.
     *
     * @param paramName param name
     * @param param     param value
     * @return current builder
     */
    public HttpRequestBuilder parameter(String paramName, Object param) {
        Object paramValues = this.parameters.get(paramName);

        if (paramValues == null) {
            this.parameters.put(paramName, param);
        }

        return this;
    }

    /**
     * append path section.
     *
     * @param path path section.
     * @return current builder
     */
    public HttpRequestBuilder path(String path) {
        Assert.notNull(path, "Path must not be null.");
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        this.path.append(path.trim());
        return this;
    }

    /**
     * Remove header with given name.
     *
     * @param headerName header name
     * @return current builder
     */
    public HttpRequestBuilder removeHeader(String headerName) {
        headers.remove(headerName);
        return this;
    }

    /**
     * Remove a parameter with param name.
     *
     * @param paramName param name
     * @return current builder
     */
    public HttpRequestBuilder removeParameter(String paramName) {
        Object paramValues = this.parameters.get(paramName);
        if (paramValues != null) {
            this.parameters.remove(paramName);
        }
        return this;
    }
}
