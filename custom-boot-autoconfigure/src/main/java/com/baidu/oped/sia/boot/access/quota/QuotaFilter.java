package com.baidu.oped.sia.boot.access.quota;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.exception.TooManyRequestException;
import com.baidu.oped.sia.boot.limit.IpStats;
import com.baidu.oped.sia.boot.limit.LimiterConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created by mason on 11/10/15.
 */
public class QuotaFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(QuotaFilter.class);

    private IpStats ipStats;

    QuotaFilter(LimiterConfig limiterConfig) {
        Assert.notNull(limiterConfig, "LimitConfig must not be null.");
        this.ipStats = new IpStats(
                limiterConfig.getMaxRequestsPerPeriod(),
                limiterConfig.getPeriodInMs(),
                limiterConfig.getBandTimeInMs());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String remoteAddr = request.getRemoteAddr();

        if (ipStats.shouldLimit(remoteAddr) && !inWhiteList()) {
            LOG.info("Too many requests with uri: {} from {}, forbidden this.", request.getRequestURI(), remoteAddr);
            throw new TooManyRequestException();
        } else {
            filterChain.doFilter(request, response);
        }
    }


    private boolean inWhiteList() {
        return RequestInfoHolder.inWhiteList();
    }
}
