package com.baidu.oped.sia.boot.client.http;

import com.baidu.oped.sia.boot.common.BasicResponse;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Http request build test case.
 *
 * @author mason
 */
public class HttpRequestBuilderTest {
    private final String endpoint = "http://localhost:8888";
    private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
            = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());

    private RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
    private HttpRequestBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = HttpRequestBuilder.get(endpoint);
        builder.acceptJson().path("/persons").parameter("num", 10000).header("Accept-Encoding", "gzip")
                .method(HttpMethod.GET);
    }

    @Test
    public void testGetParameters() throws Exception {
        ResponseEntity<BasicResponse> forEntity = restTemplate
                .exchange(builder.getRequestUri(), builder.getMethod(), builder.getEntity(), BasicResponse.class,
                        (Map<String, ?>) builder.getParameters());
        System.out.println(forEntity);

        builder.removeHeader("Accept-Encoding");
        forEntity = restTemplate
                .exchange(builder.getRequestUri(), builder.getMethod(), builder.getEntity(), BasicResponse.class,
                        (Map<String, ?>) builder.getParameters());
        System.out.println(forEntity);
    }

    @Test
    public void testGetHeaders() throws Exception {

    }

    @Test
    public void testGetEntity() throws Exception {

    }

    @Test
    public void testGetRequestUri() throws Exception {

    }

    @Test
    public void testEntity() throws Exception {

    }

    @Test
    public void testAcceptXml() throws Exception {

    }

    @Test
    public void testAcceptJson() throws Exception {

    }

    @Test
    public void testRemoveHeader() throws Exception {

    }

    @Test
    public void testHeader() throws Exception {

    }

    @Test
    public void testAddHeader() throws Exception {

    }

    @Test
    public void testRemoveParameter() throws Exception {

    }

    @Test
    public void testParameter() throws Exception {

    }

    @Test
    public void testAddParameter() throws Exception {

    }

    @Test
    public void testPath() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }
}