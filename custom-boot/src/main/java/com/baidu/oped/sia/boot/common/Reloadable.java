package com.baidu.oped.sia.boot.common;

/**
 * Reload the config properties.
 *
 * @author mason
 */
public interface Reloadable<T extends ConfigProperties> {

    /**
     * Reload the reload object.
     */
    void reload();

    /**
     * Get the reload object.
     *
     * @return the reload object
     */
    T getReload();
}
