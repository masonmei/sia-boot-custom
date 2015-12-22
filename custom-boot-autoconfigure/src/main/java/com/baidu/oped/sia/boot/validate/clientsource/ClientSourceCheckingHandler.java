package com.baidu.oped.sia.boot.validate.clientsource;

import com.baidu.oped.sia.boot.exception.RequestForbiddenException;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Client Source Checking handler.
 *
 * @author mason
 */
public class ClientSourceCheckingHandler {

    private final ClientSourceChecker clientSourceChecker;
    private final ClientSourceCheckingProperties properties;
    private final ClientSourceWhiteLabel clientSourceWhiteLabel;

    /**
     * Construct handler with configuration properties and white label.
     *
     * @param properties             configuration properties.
     * @param clientSourceWhiteLabel white label
     */
    public ClientSourceCheckingHandler(ClientSourceCheckingProperties properties,
                                       ClientSourceWhiteLabel clientSourceWhiteLabel) {
        Assert.notNull(properties);
        Assert.notNull(clientSourceWhiteLabel);
        this.properties = properties;
        this.clientSourceWhiteLabel = clientSourceWhiteLabel;
        this.clientSourceChecker = new ActionClientSourceChecker(properties.getPattern());
    }

    /**
     * Valid if allowed.
     *
     * @param targets validate target
     */
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

    private boolean isFromValidateSource() {
        String remoteAddr = getRequest().getRemoteAddr();
        if (clientSourceWhiteLabel.isWhiteAddress(remoteAddr)) {
            return true;
        }

        String remoteHost = getRequest().getRemoteHost();
        return clientSourceWhiteLabel.isWhiteHost(remoteHost);
    }

    /**
     * Provide the ability to get HttpServletRequest.
     *
     * @return the request
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    private ClientSourceCheckingProperties getProperties() {
        return properties;
    }
}
