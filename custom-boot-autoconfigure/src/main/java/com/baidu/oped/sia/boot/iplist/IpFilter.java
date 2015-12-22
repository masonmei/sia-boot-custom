package com.baidu.oped.sia.boot.iplist;

import com.baidu.oped.sia.boot.common.FileWatcher;
import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.exception.RequestForbiddenException;
import com.baidu.oped.sia.boot.utils.IpV4Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ip Filter to limit only the not in black list request to be continue.
 *
 * @author mason
 */
public class IpFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(IpFilter.class);

    private final FileWatcher<IpHolder> fileWatcher;

    public IpFilter(FileWatcher<IpHolder> fileWatcher) {
        Assert.notNull(fileWatcher, "IpHolder watcher must not be null.");
        this.fileWatcher = fileWatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        LOG.debug("filter request: {} from {} with IpFilter", request.getRequestURI(), ip);
        List<String> blackSet = fileWatcher.getHolder().getContext().getDeny();
        List<String> whiteSet = fileWatcher.getHolder().getContext().getAllow();

        if (blackSet != null) {
            for (String blackRange : blackSet) {
                if (IpV4Utils.isInRange(blackRange, ip)) {
                    LOG.debug("request with url: {} from ip: {} has been blocked", request.getRequestURI(), ip);
                    throw new RequestForbiddenException();
                }
            }
        }

        if (whiteSet != null && whiteSet.contains(ip)) {
            LOG.debug("request: {} from {} has in the white list.", request.getRequestURI(), ip);
            RequestInfoHolder.setInWhiteList(true);
        }

        filterChain.doFilter(request, response);
        RequestInfoHolder.removeInWhiteList();
    }
}
