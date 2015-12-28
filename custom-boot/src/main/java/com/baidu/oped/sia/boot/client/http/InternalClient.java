package com.baidu.oped.sia.boot.client.http;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.ContextResolver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

public class InternalClient {

    private static Client CLIENT;
    private static ObjectMapper DEFAULT_MAPPER;
    private static ObjectMapper WRAPPED_MAPPER;

    private static Logger loggerFilter = Logger.getLogger(InternalClient.class.getName());

    static {
        initialize();
    }
    private static void initialize() {
        try{
            // use apache connector to sent PATCH HttpMethod
            // CLIENT = ClientBuilder.newBuilder().build(); // default connector java, not used now
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.connectorProvider(new ApacheConnectorProvider());

            // 1. set connection pool，default connection Manager has bug, not close
            // 2. baidu bgw close idle connetion > 90s
            // connection pool must drop idle connection itself to fix TCP retransmission bug
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(60,
                    TimeUnit.SECONDS);
            connectionManager.setMaxTotal(400);
            connectionManager.setDefaultMaxPerRoute(20);
            clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
            CLIENT = ClientBuilder.newClient(clientConfig);

            CLIENT.property(ClientProperties.REQUEST_ENTITY_PROCESSING, RequestEntityProcessing.BUFFERED);
            CLIENT.property(ClientProperties.CONNECT_TIMEOUT, 1000);
            CLIENT.property(ClientProperties.READ_TIMEOUT, 10000);

            DEFAULT_MAPPER = new ObjectMapper();

            DEFAULT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            DEFAULT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
            DEFAULT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            DEFAULT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            WRAPPED_MAPPER = new ObjectMapper();

            WRAPPED_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            WRAPPED_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
            WRAPPED_MAPPER.enable(SerializationFeature.WRAP_ROOT_VALUE);
            WRAPPED_MAPPER.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            WRAPPED_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            WRAPPED_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            CLIENT.register(new JacksonFeature()).register(new ContextResolver<ObjectMapper>() {
                public ObjectMapper getContext(Class<?> type) {
                    return type.getAnnotation(JsonRootName.class) == null ? DEFAULT_MAPPER : WRAPPED_MAPPER;
                }
            });

            CLIENT.register(new ClientRequestFilter() {
                public void filter(ClientRequestContext requestContext) throws IOException {
                    requestContext.getHeaders().remove("Content-Language");
                    requestContext.getHeaders().remove("Content-Encoding");
                }
            });

            LoggingFilter filter = new LoggingFilter(loggerFilter, true);
            CLIENT.register(filter);

        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static InternalRequest request(String endpoint) {
        InternalRequest request = new InternalRequest(CLIENT);
        return request.endpoint(endpoint);
    }

}
