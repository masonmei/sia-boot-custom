package com.baidu.oped.sia.boot.limit;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Ip Stats.
 *
 * @author mason
 */
public class IpStats {
    private final int maxRequestsPerPeriod;
    private final int periodInMs;
    private final int bandTimeInMs;
    private ConcurrentHashMap<String, IpTracker> ipTrackerMap = new ConcurrentHashMap<>();

    /**
     * Ip Stats Constructor.
     *
     * @param maxRequestsPerPeriod max requests per period
     * @param periodInMs           period length
     * @param bandTimeInMs         band time when too many request
     */
    public IpStats(int maxRequestsPerPeriod, int periodInMs, int bandTimeInMs) {
        this.maxRequestsPerPeriod = maxRequestsPerPeriod;
        this.periodInMs = periodInMs;
        this.bandTimeInMs = bandTimeInMs;
    }

    /**
     * Check if the ip address should be limit.
     *
     * @param remoteAddr remote ip address
     * @return check result
     */
    public boolean shouldLimit(String remoteAddr) {
        long currentTimeInMillis = System.currentTimeMillis();
        if (ipTrackerMap.containsKey(remoteAddr)) {
            IpTracker ipTracker = ipTrackerMap.get(remoteAddr);
            if (ipTracker != null && ipTracker.hasReachedLimit(maxRequestsPerPeriod, currentTimeInMillis)) {
                return true;
            }
        } else {
            IpTracker ipTracker = new IpTracker(remoteAddr, currentTimeInMillis, periodInMs, bandTimeInMs);
            ipTrackerMap.put(remoteAddr, ipTracker);
        }
        return false;
    }
}
