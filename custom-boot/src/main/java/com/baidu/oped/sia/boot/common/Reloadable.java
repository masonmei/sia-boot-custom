package com.baidu.oped.sia.boot.common;

/**
 * Reload the config properties.
 *
 * @author mason
 */
public interface Reloadable<T extends ConfigProperties> {

    /**
     * Get the reload object.
     *
     * @return the reload object
     */
    T getReload();

    /**
     * Reload the reload object.
     */
    void reload();
}
