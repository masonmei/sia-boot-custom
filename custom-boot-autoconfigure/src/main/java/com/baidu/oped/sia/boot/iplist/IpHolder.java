package com.baidu.oped.sia.boot.iplist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mason on 10/29/15.
 */
public class IpHolder {
    private List<String> deny = new ArrayList<>();
    private List<String> allow = new ArrayList<>();

    public List<String> getDeny() {
        return deny;
    }

    public void setDeny(List<String> deny) {
        this.deny = Collections.unmodifiableList(deny == null ? Collections.<String>emptyList() : deny);
    }

    public List<String> getAllow() {
        return allow;
    }

    public void setAllow(List<String> allow) {
        this.allow = Collections.unmodifiableList(allow == null ? Collections.<String>emptyList() : allow);
    }

}
