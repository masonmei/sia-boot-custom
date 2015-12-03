package com.baidu.oped.sia.boot.limit;

import java.util.Collections;
import java.util.List;

/**
 * Created by mason on 11/10/15.
 */
public class WhiteListIpHolder {
    private List<String> allow;

    public List<String> getAllow() {
        return allow;
    }

    public void setAllow(List<String> allow) {
        this.allow = Collections.unmodifiableList(allow == null ? Collections.<String>emptyList() : allow);
    }
}
