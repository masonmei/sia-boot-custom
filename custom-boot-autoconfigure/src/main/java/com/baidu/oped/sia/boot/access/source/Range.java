package com.baidu.oped.sia.boot.access.source;

import com.baidu.oped.sia.boot.common.ConfigProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Source Range.
 *
 * @author mason
 */
public class Range implements ConfigProperties {
    private static final long serialVersionUID = 4330895855668555732L;

    private Type type;
    private List<String> ranges = new ArrayList<>();

    public List<String> getRanges() {
        return ranges;
    }

    public void setRanges(List<String> ranges) {
        this.ranges = ranges;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
