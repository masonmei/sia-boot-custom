package com.baidu.oped.sia.boot.access;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 12/14/15.
 */
public class AccessControlProperties {
    private List<String> blackLabels = new ArrayList<>();
    private List<String> whiteLabels = new ArrayList<>();
    private Quota quota = new Quota();
}
