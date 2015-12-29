package com.baidu.oped.sia.boot.access.source;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Ip Limitation, if specified, only the client from the given ip scopes can visit the endpoint.
 *
 * @author mason
 */
public class Limit {

    private String[] paths = new String[0];
    private RequestMethod[] methods = new RequestMethod[0];
    private List<Range> ranges = new ArrayList<>();

    public RequestMethod[] getMethods() {
        return methods;
    }

    public void setMethods(RequestMethod[] methods) {
        this.methods = methods;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public List<Range> getRanges() {
        return ranges;
    }

    public void setRanges(List<Range> ranges) {
        this.ranges = ranges;
    }
}
