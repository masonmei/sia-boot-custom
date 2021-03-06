package com.baidu.oped.sia.boot.decode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet Request Uri decode support Filter.
 *
 * @author mason
 */
public class DecodeUriWrapperFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(DecodeUriWrapperFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LOG.debug("decode uri : {}", request.getRequestURI());
        filterChain.doFilter(new DecodeUriWrapper(request), response);
    }
}
