package com.baidu.oped.sia.boot.limit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Ip Tracker.
 *
 * @author mason
 */
public class IpTracker {
    private final String ipAddress;
    private final List<Long> timestamps;
    private final int bandTimeInMillis;
    private final int periodInMillis;
    private Long bandUntilTime;

    /**
     * Default constructor.
     *
     * @param ipAddress           remote ip address
     * @param currentTimeInMillis current time
     * @param periodInMillis      period length
     * @param bandTimeInMillis    band time
     */
    public IpTracker(String ipAddress, long currentTimeInMillis, int periodInMillis, int bandTimeInMillis) {
        this.ipAddress = ipAddress;
        this.bandTimeInMillis = bandTimeInMillis;
        this.periodInMillis = periodInMillis;
        this.timestamps = new CopyOnWriteArrayList<>();
        addEntry(currentTimeInMillis);
        this.bandUntilTime = currentTimeInMillis - bandTimeInMillis;
    }

    private void addEntry(long currentTimeInMillis) {
        fixRange(currentTimeInMillis);
        timestamps.add(currentTimeInMillis);
    }

    private void fixRange(long currentTimeInMillis) {
        List<Long> toRemove = new ArrayList<>();
        for (Long timestamp : timestamps) {
            if (timestamp <= currentTimeInMillis - periodInMillis) {
                toRemove.add(timestamp);
            }
        }

        if (toRemove.size() > 0) {
            timestamps.removeAll(toRemove);
        }
    }

    /**
     * Check meet the period limitation.
     *
     * @param maxRequestsPerPeriod period limitation
     * @param currentTimeInMillis  current time
     * @return check result
     */
    public boolean hasReachedLimit(int maxRequestsPerPeriod, long currentTimeInMillis) {
        if (currentTimeInMillis < bandUntilTime) {
            return true;
        }

        addEntry(currentTimeInMillis);
        if (timestamps.size() >= maxRequestsPerPeriod) {
            this.bandUntilTime = bandTimeInMillis + currentTimeInMillis;
            return true;
        }

        return false;
    }
}
