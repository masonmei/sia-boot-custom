/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

/**
 * Created by mason on 7/14/15.
 */
public interface ClientSourceWhiteLabel {
    /**
     * Check if the given ipAddress is in the white address list.
     *
     * @param address
     *
     * @return
     */
    boolean isWhiteAddress(String address);

    /**
     * Check if the given host is in white host list.
     *
     * @param host
     *
     * @return
     */
    boolean isWhiteHost(String host);

}
