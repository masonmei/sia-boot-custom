package com.baidu.oped.sia.boot.rewrite;

import com.baidu.oped.sia.boot.utils.ReplaceUtils;

import org.springframework.util.Assert;

import java.util.Map;
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
     * Path rewrite request wrapper
     *
     * @param request request to wrap
     * @param rewrite rewrite uri
     * @param params  params to resole
     */
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
