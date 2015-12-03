package com.baidu.oped.sia.boot.limit;

import com.baidu.oped.sia.boot.common.FileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mason on 11/10/15.
 */
public class SimpleLimiterFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleLimiterFilter.class);

    private final FileWatcher<WhiteListIpHolder> fileWatcher;
    private final LimiterConfig limiterConfig;

    private IpStats ipStats;

    SimpleLimiterFilter(FileWatcher<WhiteListIpHolder> fileWatcher, LimiterConfig limiterConfig) {
        Assert.notNull(fileWatcher, "WhiteListIpHolder watcher must not be null.");
        Assert.notNull(limiterConfig, "LimitConfig must not be null.");
        this.fileWatcher = fileWatcher;
        this.limiterConfig = limiterConfig;
        this.ipStats = new IpStats(
                limiterConfig.getMaxRequestsPerPeriod(),
                limiterConfig.getPeriodInMs(),
                limiterConfig.getBandTimeInMs());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Initialing SimpleLimiterFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String remoteAddr = servletRequest.getRemoteAddr();

        if (ipStats.shouldLimit(remoteAddr) && !inWhiteList(remoteAddr)) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().append(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
            LOG.info("Blocked IP: {}", remoteAddr);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean inWhiteList(String remoteAddr) {
        List<String> whiteSet = fileWatcher.getIpListHolder().getAllow();
        return whiteSet != null && whiteSet.contains(remoteAddr);
    }

    @Override
    public void destroy() {
        LOG.info("Destroying SimpleLimiterFilter");
        ipStats = null;
    }
}
