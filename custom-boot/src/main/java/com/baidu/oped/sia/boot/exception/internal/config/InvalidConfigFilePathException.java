package com.baidu.oped.sia.boot.exception.internal.config;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.INVALID_CONFIGURATION_FILE_PATH;

/**
 * Invalid Configuration File Path exception.
 *
 * @author mason
 */
public class InvalidConfigFilePathException extends ApplicationConfigurationException {
    public InvalidConfigFilePathException(Object[] args) {
        super(INVALID_CONFIGURATION_FILE_PATH, args);
    }
}
