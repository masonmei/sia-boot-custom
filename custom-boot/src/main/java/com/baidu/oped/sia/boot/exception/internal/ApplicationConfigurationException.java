package com.baidu.oped.sia.boot.exception.internal;

import static com.baidu.oped.sia.boot.exception.SystemCode.INCORRECT_CONFIGURATION;

import com.baidu.oped.sia.boot.exception.SystemException;

/**
 * Application Configuration Exception when the system configuration is not correct.
 *
 * @author mason
 */
public class ApplicationConfigurationException extends SystemException {

    public ApplicationConfigurationException(String message, Object[] args) {
        super(INCORRECT_CONFIGURATION, message, args);
    }
}
