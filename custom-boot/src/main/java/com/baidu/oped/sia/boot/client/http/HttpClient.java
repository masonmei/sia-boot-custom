package com.baidu.oped.sia.boot.client.http;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mason on 12/28/15.
 */
public class HttpClient {

    private final ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().build());
    private RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
}
