package com.baidu.oped.sia.boot.iam.internal;

import static com.baidu.oped.cloudwatch.business.common.iam.internal.BccVmDetailRequest.BCC_AUTH_HEADER_NAME;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

import com.baidu.oped.cloudwatch.business.client.http.Entity;
import com.baidu.oped.cloudwatch.business.client.http.InternalRequest;
import com.baidu.oped.cloudwatch.business.client.http.InternalResponse;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.PostConstruct;

/**
 * Created by mason on 12/16/15.
 */
public class IamBccContext {
    private static final Logger LOG = LoggerFactory.getLogger(IamBccContext.class);

    private static final Cache<String, String> CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(1, HOURS)
            .expireAfterAccess(5, MINUTES)
            .maximumSize(10000)
            .build();

    private final ScheduledExecutorService executorService;
    private final IamAuthRequest authRequest;

    private String authToken;
    private String bccEndpoint;

    public IamBccContext(IamAuthRequest authRequest) {
        CustomizableThreadFactory customizableThreadFactory = new CustomizableThreadFactory();
        customizableThreadFactory.setThreadNamePrefix("bcm-iam-bcc-");
        this.executorService = Executors.newScheduledThreadPool(1, customizableThreadFactory);
        this.authRequest = authRequest;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getBccEndpoint() {
        return bccEndpoint;
    }

    public String getUserIdFromContext(final String vmUuid, final String remoteAddr) {
        String key = String.format("%s-%s", vmUuid, remoteAddr);
        try {
            return CACHE.get(key, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    BccVmDetailResponse response = getVmDetailResponse(vmUuid);
                    if (response != null && response.getFloatingIps().contains(remoteAddr)) {
                        return response.getServer().getUserId();
                    }
                    return "";
                }
            });
        } catch (ExecutionException e) {
            LOG.warn("invoke bcc service to verify vm with id {} failed.", vmUuid, e);
        }
        return null;
    }

    public void refreshToken() {
        InternalRequest request = Client.createRequest();
        request.acceptJson();
        request.endpoint(authRequest.getRequestEndpoint());
        InternalResponse response = request.postWithResponse(Entity.json(authRequest.getRequestBody()));
        String header = response.header(IamAuthRequest.TOKEN_HEADER_NAME);
        this.authToken = header;
        this.bccEndpoint = response.getEntity(IamAuthResponse.class).getPublicEndpoint("compute");
    }

    private BccVmDetailResponse getVmDetailResponse(String vmUuid) {
        InternalRequest request = Client.createRequest();
        BccVmDetailRequest vmDetailRequest = new BccVmDetailRequest();
        vmDetailRequest.setAuthToken(getAuthToken());
        vmDetailRequest.setEndpoint(getBccEndpoint());
        vmDetailRequest.setVmUuid(vmUuid);
        request.acceptJson()
                .endpoint(vmDetailRequest.getRequestTarget())
                .header(BCC_AUTH_HEADER_NAME, getAuthToken());
        return request.get(BccVmDetailResponse.class);
    }

    @PostConstruct
    private void init() {
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshToken();
            }
        }, 0, 50, MINUTES);
    }

}
