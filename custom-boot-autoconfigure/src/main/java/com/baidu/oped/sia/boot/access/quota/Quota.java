package com.baidu.oped.sia.boot.access.quota;

import static com.baidu.oped.sia.boot.access.quota.Level.SERVER;

import com.baidu.oped.sia.boot.common.ConfigProperties;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

/**
 * Quota Configuration.
 *
 * @author mason
 */
public class Quota implements ConfigProperties {

    private static final long serialVersionUID = -6698703405867714510L;

    private Level level = SERVER;
    private String[] paths = new String[0];
    private RequestMethod[] methods = new RequestMethod[0];
    private int quota = 100;
    private int period = 10;
    private int bandTime = 60;

    public int getQuota() {
        return quota;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String[] getPaths() {
        return Arrays.copyOf(paths, paths.length);
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public RequestMethod[] getMethods() {
        return Arrays.copyOf(methods, methods.length);
    }

    public void setMethods(RequestMethod[] methods) {
        this.methods = methods;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getBandTime() {
        return bandTime;
    }

    public void setBandTime(int bandTime) {
        this.bandTime = bandTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("level", level)
                .add("paths", paths)
                .add("methods", methods)
                .add("quota", quota)
                .add("period", period)
                .add("bandTime", bandTime)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Quota quota1 = (Quota) other;
        return quota == quota1.quota
               && period == quota1.period
               && bandTime == quota1.bandTime
               && level == quota1.level
               && Objects.equal(paths, quota1.paths)
               && Objects.equal(methods, quota1.methods);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level, paths, methods, quota, period, bandTime);
    }
}
