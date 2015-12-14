package com.baidu.oped.sia.boot.iam;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.AUTH_INVALID_SERVICE;
import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.AUTH_INVALID_USER;
import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.AUTH_SERVICE_NOT_AUTHENTICATED;
import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.AUTH_USER_NOT_AUTHENTICATED;
import static com.baidu.oped.sia.boot.utils.Constrains.AUTHORIZATION;

import static org.springframework.util.StringUtils.collectionToDelimitedString;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.bce.iam.IamClient;
import com.baidu.bce.iam.IamException;
import com.baidu.bce.iam.SignatureAuthentication;
import com.baidu.bce.iam.internal.Token;
import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.exception.AuthenticationFailedException;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mason on 12/2/15.
 */
public class DefaultIamManager implements IamManager {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultIamManager.class);

    private IamClient iamClient;
    private Map<String, IamProperties.ServiceAccount> serviceAccountMap = new HashMap();

    @Override
    public void checkUserAuth(String userId) {
        if (RequestInfoHolder.ignoreAuth()) {
            return;
        }

        String currentUser = RequestInfoHolder.currentUser();
        if (currentUser == null) {
            throw new AuthenticationFailedException(AUTH_USER_NOT_AUTHENTICATED);
        }
        if (!currentUser.equals(userId)) {
            throw new AuthenticationFailedException(AUTH_INVALID_USER);
        }
    }

    @Override
    public void checkServiceAuth(String scope) {
        if (RequestInfoHolder.ignoreAuth()) {
            return;
        }

        String currentUser = RequestInfoHolder.currentUser();
        if (currentUser == null) {
            throw new AuthenticationFailedException(AUTH_SERVICE_NOT_AUTHENTICATED);
        }

        String userScope = getServiceScope(currentUser);
        if (userScope == null || !userScope.equals(scope)) {
            throw new AuthenticationFailedException(AUTH_INVALID_SERVICE);
        }
    }

    @Override
    public void checkServiceAndUserAuth(String scope, String userId) {
        if (RequestInfoHolder.ignoreAuth()) {
            return;
        }

        String currentUser = RequestInfoHolder.currentUser();
        if (currentUser == null) {
            throw new AuthenticationFailedException(AUTH_SERVICE_NOT_AUTHENTICATED);
        }

        if (currentUser.equals(userId)) {
            return;
        }

        String userScope = getServiceScope(currentUser);
        if (userScope == null || !userScope.equals(scope)) {
            throw new AuthenticationFailedException(AUTH_INVALID_SERVICE);
        }
    }

    @Override
    public String getUserFromIam(HttpServletRequest servletRequest) {

        String requestURI = servletRequest.getRequestURI();

        String authorization = servletRequest.getHeader(AUTHORIZATION);
        SignatureAuthentication.Request userRequest = buildSignatureAuthenticationRequest(servletRequest);

        LOG.debug("iam authenticate info,authorization:{},userRequest:{}", authorization,
                new Gson().toJson(userRequest));
        try {
            SignatureAuthentication sigAuth = new SignatureAuthentication(authorization, userRequest);
            Token token = iamClient.authenticate(sigAuth);
            String userId = token.getUser().getId();
            LOG.info("iam authenticate success,url:{},token:{}", requestURI, userId);
            return userId;
        } catch (IamException ex) {
            LOG.warn("iam authenticate fail,url:{},errorCode:{},errorMsg:{}", requestURI, ex.getErrorCode(),
                    ex.getErrMsg());
            return null;
        }
    }

    private SignatureAuthentication.Request buildSignatureAuthenticationRequest(HttpServletRequest request) {
        SignatureAuthentication.Request userRequest = new SignatureAuthentication.Request();
        userRequest.setUri(request.getRequestURI());
        userRequest.setMethod(request.getMethod());

        // if user not set header content-type=application/json, put metricdata api will take metric data body as
        // parameter key, and then will generate iam signature error
//        boolean hasParameter = true;
//        if (request.getRequestURI().startsWith("/json-api/v1/metricdata") && request.getMethod().equals("POST")) {
//            hasParameter = false;
//        }

        // Should care that a parameter may have multiple values. But I have no idea here how to combine more than one
        // values and which delimiter should use. So just pick the first value. It is protocol's bug in fact.
//        if (hasParameter) {
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements(); ) {
            String key = e.nextElement();
            List<String> valueArray = new ArrayList<>();
            Collections.addAll(valueArray, request.getParameterValues(key));

            if (valueArray.size() > 1) {
                Collections.sort(valueArray);
                userRequest.addParam(key, collectionToDelimitedString(valueArray, ";"));
            } else {
                userRequest.addParam(key, valueArray.get(0));
            }
        }
//        }

        for (Enumeration<String> h = request.getHeaderNames(); h.hasMoreElements(); ) {
            String header = h.nextElement();
            ArrayList<String> headerValues = new ArrayList<>();
            for (Enumeration<String> e = request.getHeaders(header); e.hasMoreElements(); ) {
                String value = e.nextElement();
                headerValues.add(value);
            }

            // Here we just use colon to join multiple values. In http colon is the most commonly delimiter for
            // http header attributes, but not the only one. So should suggest clients do not use multiple values in
            // http headers.
            if (headerValues.size() > 1) {
                Collections.sort(headerValues);
                ;
                userRequest.addHeader(header, collectionToDelimitedString(headerValues, ";"));
            } else {
                userRequest.addHeader(header, headerValues.get(0));
            }
        }

        return userRequest;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    private String getServiceScope(String serviceUserId) {
        IamProperties.ServiceAccount serviceAccount = serviceAccountMap.get(serviceUserId);
        if (serviceAccount == null) {
            return null;
        }
        return serviceAccount.getScope();
    }

    public void setIamClient(IamClient iamClient) {
        this.iamClient = iamClient;
    }

    public void setServiceAccounts(List<IamProperties.ServiceAccount> serviceAccounts) {
        this.serviceAccountMap.clear();
        for (IamProperties.ServiceAccount serviceAccount : serviceAccounts) {
            this.serviceAccountMap.put(serviceAccount.getUserId(), serviceAccount);
        }
    }
}
