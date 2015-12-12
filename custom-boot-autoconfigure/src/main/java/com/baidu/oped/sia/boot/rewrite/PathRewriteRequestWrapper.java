package com.baidu.oped.sia.boot.rewrite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.util.Map;

import com.baidu.oped.sia.boot.utils.ReplaceUtils;

import org.springframework.util.Assert;

/**
 * Path Rewrite Http Servlet Request Wrapper.
 * <p>
 * Created by mason on 12/2/15.
 */
class PathRewriteRequestWrapper extends HttpServletRequestWrapper {

    private final String servletPath;

    public PathRewriteRequestWrapper(HttpServletRequest request, String rewrite, Map<String, String> params) {
        super(request);
        Assert.hasLength(rewrite, "Rewrite Path must not be null.");
        Assert.notNull(params, "Params must not be null for rewrite Path");
        this.servletPath = ReplaceUtils.replacePattern(rewrite, params);
    }

    @Override
    public String getServletPath() {
        return servletPath;
    }

}
