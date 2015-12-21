package com.baidu.oped.sia.boot.limit;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mason on 11/10/15.
 */
public class IpStats {
    private final int maxRequestsPerPeriod;
    private final int periodInMs;
    private final int bandTimeInMs;
    private ConcurrentHashMap<String, IpTracker> ipTrackerMap = new ConcurrentHashMap<>();

    public IpStats(int maxRequestsPerPeriod, int periodInMs, int bandTimeInMs) {
        this.maxRequestsPerPeriod = maxRequestsPerPeriod;
        this.periodInMs = periodInMs;
        this.bandTimeInMs = bandTimeInMs;
    }

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
