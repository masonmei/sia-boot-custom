/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.logheader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Log Header Request Filter.
 *
 * @author mason
 */
public class LogHeaderRequestFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(LogHeaderRequestFilter.class);

    private final Set<String> headerNames;

    public LogHeaderRequestFilter(List<String> headerNames) {
        Assert.notNull(headerNames, "LogHeaderRequestFilter headerNames may not be null.");
        this.headerNames = new HashSet<>(headerNames);
    }

    @Override
    public void destroy() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!CollectionUtils.isEmpty(headerNames)) {
            LOG.debug("log headers {} of request {} from {}.", headerNames, request.getRequestURI(),
                    request.getRemoteAddr());
            logHeaders(request);
        }
        filterChain.doFilter(request, response);
    }

    private void logHeaders(HttpServletRequest request) {
        for (String headerName : headerNames) {
            LOG.info(request.getHeader(headerName));
        }
    }
}
