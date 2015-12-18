/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

/**
 * Client source white label interface.
 *
 * @author mason
 */
public interface ClientSourceWhiteLabel {
    /**
     * Check if the given ipAddress is in the white address list.
     *
     * @param address checking the source inet address
     * @return check result
     */
    boolean isWhiteAddress(String address);

    /**
     * Check if the given host is in white host list.
     *
     * @param host checking the source host name
     * @return check result
     */
    boolean isWhiteHost(String host);

}
