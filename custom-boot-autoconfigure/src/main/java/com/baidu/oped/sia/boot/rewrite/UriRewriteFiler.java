package com.baidu.oped.sia.boot.rewrite;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created by mason on 12/2/15.
 */
public class UriRewriteFiler extends OncePerRequestFilter {

    private UriRewriteParameterResolver parameterResolver;

    private RewriteContext rewriteContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Map<String, String> params;
        if (rewriteContext.isResolveParam()) {
            params = parameterResolver.resolve(request);
        } else {
            params = Collections.emptyMap();
        }

        String servletPath = request.getServletPath();
        for (RewriteContext.RewriteEntry entry : rewriteContext.getRewriteEntries()) {
            if (servletPath.matches(entry.getPattern())) {
                filterChain.doFilter(new PathRewriteRequestWrapper(request, entry.getRewrite(), params), response);
                break;
            }
        }
    }

    public void setParameterResolver(UriRewriteParameterResolver parameterResolver) {
        this.parameterResolver = parameterResolver;
    }

    public void setRewriteContext(RewriteContext rewriteContext) {
        this.rewriteContext = rewriteContext;
    }
}
