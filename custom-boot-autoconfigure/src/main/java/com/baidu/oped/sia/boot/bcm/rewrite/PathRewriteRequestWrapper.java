package com.baidu.oped.sia.boot.bcm.rewrite;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Path Rewrite Http Servlet Request Wrapper.
 *
 * @author mason
 */
class PathRewriteRequestWrapper extends HttpServletRequestWrapper {

    private final String servletPath;

    /**
     * Path rewrite request wrapper.
     *
     * @param request request to wrap
     * @param rewrite rewrite uri
     */
    public PathRewriteRequestWrapper(HttpServletRequest request, String rewrite) {
        super(request);
        Assert.hasLength(rewrite, "Rewrite Path must not be null.");
        this.servletPath = rewrite;
    }

    @Override
    public String getServletPath() {
        return servletPath;
    }

}
