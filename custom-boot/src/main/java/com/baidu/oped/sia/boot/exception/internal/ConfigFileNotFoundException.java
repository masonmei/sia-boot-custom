package com.baidu.oped.sia.boot.exception.internal;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.CONFIGURATION_FILE_NOT_FOUND;

/**
 * Exceptions when the configuration file not found.
 *
 * @author mason
 */
public class ConfigFileNotFoundException extends ApplicationConfigurationException {
    public ConfigFileNotFoundException(Object[] args) {
        super(CONFIGURATION_FILE_NOT_FOUND, args);
    }
}
