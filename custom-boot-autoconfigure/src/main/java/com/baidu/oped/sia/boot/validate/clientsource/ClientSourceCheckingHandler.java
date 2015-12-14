/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

import javax.servlet.http.HttpServletRequest;

import com.baidu.oped.sia.boot.exception.RequestForbiddenException;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by mason on 7/14/15.
 */
public class ClientSourceCheckingHandler {

    private final ClientSourceChecker clientSourceChecker;
    private final ClientSourceCheckingProperties properties;
    private final ClientSourceWhiteLabel clientSourceWhiteLabel;

    public ClientSourceCheckingHandler(ClientSourceCheckingProperties properties,
                                       ClientSourceWhiteLabel clientSourceWhiteLabel) {
        Assert.notNull(properties);
        Assert.notNull(clientSourceWhiteLabel);
        this.properties = properties;
        this.clientSourceWhiteLabel = clientSourceWhiteLabel;
        this.clientSourceChecker = new ActionClientSourceChecker(properties.getPattern());
    }

    public void validate(Object... targets) {
        boolean checkSource = false;
        for (Object target : targets) {
            if (clientSourceChecker.supports(target.getClass())) {
                checkSource = clientSourceChecker.needValidateSource(target);
            }

            if (checkSource) {
                break;
            }
        }

        if (checkSource && !isFromValidateSource()) {
            throw new RequestForbiddenException();
        }
    }

    private ClientSourceCheckingProperties getProperties() {
        return properties;
    }

    /**
     * Provide the ability to get HttpServletRequest.
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    private boolean isFromValidateSource() {
        String remoteAddr = getRequest().getRemoteAddr();
        if (clientSourceWhiteLabel.isWhiteAddress(remoteAddr)) {
            return true;
        }

        String remoteHost = getRequest().getRemoteHost();
        return clientSourceWhiteLabel.isWhiteHost(remoteHost);
    }
}
