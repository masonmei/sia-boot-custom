package com.baidu.oped.sia.boot.bcm.iam.internal;

import static com.baidu.oped.sia.boot.bcm.iam.internal.BccVmDetailRequest.BCC_AUTH_HEADER_NAME;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

import com.baidu.oped.sia.boot.client.http.HttpClient;
import com.baidu.oped.sia.boot.client.http.HttpRequestBuilder;
import com.baidu.oped.sia.boot.client.http.HttpRequestContext;
import com.baidu.oped.sia.boot.client.http.HttpTask;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.PostConstruct;

/**
 * Iam Bcc Context.
 *
 * @author mason
 */
public class IamBccContext {
    private static final Logger LOG = LoggerFactory.getLogger(IamBccContext.class);

    private static final Cache<String, String> CACHE = CacheBuilder.newBuilder().expireAfterWrite(1, HOURS)
            .expireAfterAccess(5, MINUTES).maximumSize(10000).build();

    private final ScheduledExecutorService executorService;
    private final IamAuthRequest authRequest;
    private final HttpClient client;

    private String authToken;
    private String bccEndpoint;

    /**
     * Construct a context with auth request and http client.
     *
     * @param authRequest auth request
     * @param client      client
     */
    public IamBccContext(IamAuthRequest authRequest, HttpClient client) {
        Assert.notNull(authRequest, "Auth request must not be null.");
        Assert.notNull(client, "Http client must not be null.");
        CustomizableThreadFactory customizableThreadFactory = new CustomizableThreadFactory();
        customizableThreadFactory.setThreadNamePrefix("bcm-iam-bcc-");
        this.client = client;
        this.executorService = Executors.newScheduledThreadPool(1, customizableThreadFactory);
        this.authRequest = authRequest;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getBccEndpoint() {
        return bccEndpoint;
    }

    /**
     * Get user id from context.
     *
     * @param vmUuid     vm uuid.
     * @param remoteAddr request source ip
     * @return user id or null
     */
    public String getUserIdFromContext(final String vmUuid, final String remoteAddr) {
        String key = String.format("%s-%s", vmUuid, remoteAddr);
        try {
            return CACHE.get(key, () -> {
                BccVmDetailResponse response = getVmDetailResponse(vmUuid);
                if (response != null && response.getFloatingIps().contains(remoteAddr)) {
                    return response.getServer().getUserId();
                }
                return "";
            });
        } catch (ExecutionException e) {
            LOG.warn("invoke bcc service to verify vm with id {} failed.", vmUuid, e);
        }
        return null;
    }

    /**
     * Refresh token in given interval.
     */
    public void refreshToken() {
        HttpRequestBuilder requestBuilder = HttpRequestBuilder.get(authRequest.getRequestEndpoint());
        requestBuilder.acceptJson();
        requestBuilder.method(HttpMethod.POST).entity(authRequest.getRequestBody());

        HttpRequestContext<IamAuthResponse> requestContext = new HttpRequestContext<>(requestBuilder,
                IamAuthResponse.class);
        HttpTask<IamAuthResponse> task = new HttpTask<>(requestContext);

        client.execute(task);

        IamAuthResponse result = requestContext.result();
        String header = requestContext.getHeaders().getFirst(IamAuthRequest.TOKEN_HEADER_NAME);
        if (authToken != null) {
            this.authToken = header;
        }
        if (result != null) {
            this.bccEndpoint = result.getPublicEndpoint("compute");
        }
    }

    private BccVmDetailResponse getVmDetailResponse(String vmUuid) {
        BccVmDetailRequest vmDetailRequest = new BccVmDetailRequest();
        vmDetailRequest.setAuthToken(getAuthToken());
        vmDetailRequest.setEndpoint(getBccEndpoint());
        vmDetailRequest.setVmUuid(vmUuid);

        HttpRequestBuilder requestBuilder = HttpRequestBuilder.get(vmDetailRequest.getRequestTarget()).acceptJson()
                .header(BCC_AUTH_HEADER_NAME, getAuthToken()).method(HttpMethod.GET);
        HttpRequestContext<BccVmDetailResponse> requestContext = new HttpRequestContext<>(requestBuilder,
                BccVmDetailResponse.class);
        HttpTask<BccVmDetailResponse> task = new HttpTask<>(requestContext);

        client.execute(task);
        return requestContext.result();
    }

    @PostConstruct
    private void init() {
        executorService.scheduleAtFixedRate((Runnable) this::refreshToken, 0, 50, MINUTES);
    }

}
