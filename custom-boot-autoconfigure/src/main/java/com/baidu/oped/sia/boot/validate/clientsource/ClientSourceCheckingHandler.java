/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

import com.baidu.oped.sia.boot.exception.SystemCode;
import com.baidu.oped.sia.boot.exception.SystemException;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mason on 7/14/15.
 */
public class ClientSourceCheckingHandler {

    private final ClientSourceChecker clientSourceChecker;
    private final ClientSourceCheckingProperties properties;
    private final ClientSourceWhiteLabel clientSourceWhiteLabel;

    public ClientSourceCheckingHandler(ClientSourceCheckingProperties properties, ClientSourceWhiteLabel clientSourceWhiteLabel) {
        Assert.notNull(properties);
        Assert.notNull(clientSourceWhiteLabel);
        this.properties = properties;
        this.clientSourceWhiteLabel = clientSourceWhiteLabel;
        this.clientSourceChecker = new ActionClientSourceChecker(properties.getPattern());
    }

    /**
     * Provide the ability to get HttpServletRequest.
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
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
            throw new SystemException(SystemCode.ACCESS_DENIED, "You are not allowed for this action.");
        }
    }

    private boolean isFromValidateSource() {
        String remoteAddr = getRequest().getRemoteAddr();
        if (clientSourceWhiteLabel.isWhiteAddress(remoteAddr)) {
            return true;
        }

        String remoteHost = getRequest().getRemoteHost();
        return clientSourceWhiteLabel.isWhiteHost(remoteHost);
    }

    private ClientSourceCheckingProperties getProperties() {
        return properties;
    }
}
