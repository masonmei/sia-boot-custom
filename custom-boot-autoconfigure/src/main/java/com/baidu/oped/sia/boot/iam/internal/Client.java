package com.baidu.oped.sia.boot.iam.internal;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.ContextResolver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.baidu.oped.cloudwatch.business.client.http.InternalRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Created by mason on 12/16/15.
 */
public class Client {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static javax.ws.rs.client.Client CLIENT;
    private static Logger loggerFilter = Logger.getLogger(Client.class.getName());

    static {
        initialize();
    }

    public static InternalRequest createRequest() {
        return new InternalRequest(CLIENT);
    }

    private static void initialize() {
        try {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.connectorProvider(new ApacheConnectorProvider());

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(60,
                    TimeUnit.SECONDS);
            connectionManager.setMaxTotal(400);
            connectionManager.setDefaultMaxPerRoute(20);
            clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
            CLIENT = ClientBuilder.newClient(clientConfig);

            CLIENT.property(ClientProperties.REQUEST_ENTITY_PROCESSING, RequestEntityProcessing.BUFFERED);
            CLIENT.property(ClientProperties.CONNECT_TIMEOUT, 1000);
            CLIENT.property(ClientProperties.READ_TIMEOUT, 10000);

            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            OBJECT_MAPPER.setDateFormat(format);


            CLIENT.register(new JacksonFeature()).register(new ContextResolver<ObjectMapper>() {
                public ObjectMapper getContext(Class<?> type) {
                    return OBJECT_MAPPER;
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

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
