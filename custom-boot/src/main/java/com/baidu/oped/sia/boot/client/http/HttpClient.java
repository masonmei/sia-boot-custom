package com.baidu.oped.sia.boot.client.http;

import com.baidu.oped.sia.boot.client.AbstractClient;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Http service invoking client.
 *
 * @author mason
 */
public class HttpClient extends AbstractClient {

    private final ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().build());
    private final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);


    @Override
    protected void handleException(RuntimeException runtimeException) {

    }
}
