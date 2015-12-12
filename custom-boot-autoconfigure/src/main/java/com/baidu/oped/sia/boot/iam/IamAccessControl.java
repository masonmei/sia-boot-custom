package com.baidu.oped.sia.boot.iam;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mason on 12/3/15.
 */
public class IamAccessControl {
    private static final Logger LOG = LoggerFactory.getLogger(IamAccessControl.class);

    private List<String> ignoredUrls = new ArrayList<>();

    public void setIgnoredUrls(List<String> ignoredUrls) {
        if (ignoredUrls != null) {
            this.ignoredUrls.clear();
            this.ignoredUrls.addAll(ignoredUrls);
        }
    }

    public boolean isNotIgnoredUri(String url) {
        url = url.toLowerCase();
        // check not need authentication url
        for (String ignored : ignoredUrls) {
            ignored = ignored.toLowerCase();
            if (matchUrlPattern(ignored, url)) {
                LOG.debug("match notNeedAuthenticationUrl rule {}", ignored);
                return false;
            }
        }
        return true;
    }

    private boolean matchUrlPattern(String urlPattern, String url) {
        if (urlPattern == null) {
            return false;
        }
        if (urlPattern.endsWith("**")) {
            String prefix = urlPattern.substring(0, urlPattern.length() - 2);
            if (url.startsWith(prefix)) {
                String suffix = url.substring(prefix.length());
                if (suffix.contains(";")) {
                    LOG.warn("security warning,url contains ';'(semicolon)  {}", url);
                    return false;
                }
                if (suffix.contains("../")) {
                    LOG.warn("security warning,url contains '../'  {}", url);
                    return false;
                }
                return true;
            }
        }
        return urlPattern.equals(url);
    }

}
