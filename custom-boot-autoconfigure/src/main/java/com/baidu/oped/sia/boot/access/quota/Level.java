package com.baidu.oped.sia.boot.access.quota;

import com.baidu.oped.sia.boot.common.ConfigProperties;

/**
 * Quota Level.
 *
 * @author mason
 */
public enum Level implements ConfigProperties {
    SERVER,
    IP,
    USER
}
