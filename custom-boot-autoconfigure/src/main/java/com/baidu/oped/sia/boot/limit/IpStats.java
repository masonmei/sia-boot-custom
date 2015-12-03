package com.baidu.oped.sia.boot.limit;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mason on 11/10/15.
 */
public class IpStats {
    private ConcurrentHashMap<String, IPTracker> ipTrackerMap = new ConcurrentHashMap<>();
    private final int maxRequestsPerPeriod;
    private final int periodInMs;
    private final int bandTimeInMs;

    public IpStats(int maxRequestsPerPeriod, int periodInMs, int bandTimeInMs) {
        this.maxRequestsPerPeriod = maxRequestsPerPeriod;
        this.periodInMs = periodInMs;
        this.bandTimeInMs = bandTimeInMs;
    }

    public boolean shouldLimit(String remoteAddr) {
        long currentTimeInMillis = System.currentTimeMillis();
        if (ipTrackerMap.containsKey(remoteAddr)) {
            IPTracker ipTracker = ipTrackerMap.get(remoteAddr);
            if(ipTracker != null && ipTracker.hasReachedLimit(maxRequestsPerPeriod, currentTimeInMillis)){
                return true;
            }
        } else {
            IPTracker ipTracker = new IPTracker(remoteAddr, currentTimeInMillis, periodInMs, bandTimeInMs);
            ipTrackerMap.put(remoteAddr, ipTracker);
        }
        return false;
    }
}
