package com.baidu.oped.sia.boot;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mason on 12/2/15.
 */
@Component
public class PathRewriteFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(new PathRewriteRequestWrapper(request), response);
    }
}

class PathRewriteRequestWrapper extends HttpServletRequestWrapper {

    public PathRewriteRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getServletPath() {
        return "/test";
    }
}
