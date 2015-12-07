/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.logheader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mason on 7/17/15.
 */
public class LogHeaderRequestFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(LogHeaderRequestFilter.class);

    private final Set<String> headerNames;

    public LogHeaderRequestFilter(List<String> headerNames) {
        Assert.notNull(headerNames, "LogHeaderRequestFilter headerNames may not be null.");
        this.headerNames = new HashSet<>(headerNames);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!CollectionUtils.isEmpty(headerNames)) {
            logHeaders(request);
        }
        filterChain.doFilter(request, response);
    }

    private void logHeaders(HttpServletRequest request) {
        for (String headerName : headerNames) {
            LOG.info(request.getHeader(headerName));
        }
    }

    @Override
    public void destroy() {

    }
}
