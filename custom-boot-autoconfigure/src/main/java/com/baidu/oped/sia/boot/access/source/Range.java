package com.baidu.oped.sia.boot.access.source;

import com.baidu.oped.sia.boot.common.ConfigProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Range.
 *
 * @author mason
 */
public class Range implements ConfigProperties {
    private RangeType rangeType;
    private List<String> ranges = new ArrayList<>();

}
