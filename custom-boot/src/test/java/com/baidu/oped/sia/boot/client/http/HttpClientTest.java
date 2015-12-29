package com.baidu.oped.sia.boot.client.http;

import static org.junit.Assert.assertNotNull;

import com.baidu.oped.sia.boot.common.BasicResponse;
import com.baidu.oped.sia.boot.exception.internal.external.ExternalInvocationException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * Created by mason on 12/29/15.
 */
public class HttpClientTest {
    private final String endpoint = "http://localhost:8888";
    private final String invalidEndpoint = "http://localhost:8881";
    private HttpClient client;

    @Before
    public void setUp() throws Exception {
        client = new HttpClient();
        client.setTimeout(300);
    }

    @Test(expected = ExternalInvocationException.class)
    public void testExecute() throws Exception {
        HttpRequestBuilder builder = HttpRequestBuilder.get(endpoint);
        builder.acceptJson().path("/persons").parameter("num", 1000).header("Accept-Encoding", "gzip")
                .method(HttpMethod.GET);
        HttpRequestContext<BasicResponse<List<Person>>> requestContext = new HttpRequestContext(builder,
                BasicResponse.class);
        client.execute(new HttpTask<>(requestContext));

        BasicResponse<List<Person>> result = requestContext.result();
        assertNotNull(result);
        assertNotNull(result.getData());

        builder = HttpRequestBuilder.get(invalidEndpoint);
        builder.acceptJson().path("/persons").parameter("num", 1000).header("Accept-Encoding", "gzip")
                .method(HttpMethod.GET);
        requestContext = new HttpRequestContext(builder, BasicResponse.class);
        client.execute(new HttpTask<>(requestContext));
    }

    @Test
    public void testHandleException() throws Exception {

    }

    @Test
    public void testPreProcessTask() throws Exception {

    }
}