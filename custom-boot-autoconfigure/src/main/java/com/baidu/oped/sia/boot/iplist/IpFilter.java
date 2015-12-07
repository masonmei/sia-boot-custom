package com.baidu.oped.sia.boot.iplist;

import com.baidu.oped.sia.boot.common.FileWatcher;
import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.exception.RequestForbiddenException;
import com.baidu.oped.sia.boot.utils.IpV4Utils;
import org.omg.PortableInterceptor.RequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mason on 10/29/15.
 */
public class IpFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(IpFilter.class);

    private final FileWatcher<IpHolder> fileWatcher;

    public IpFilter(FileWatcher<IpHolder> fileWatcher) {
        Assert.notNull(fileWatcher, "IpHolder watcher must not be null.");
        this.fileWatcher = fileWatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();

        List<String> blackSet = fileWatcher.getIpListHolder().getDeny();
        List<String> whiteSet = fileWatcher.getIpListHolder().getAllow();

        if (blackSet != null) {
            for (String blackRange : blackSet) {
                if (IpV4Utils.isInRange(blackRange, ip)) {
                    LOG.debug("request with url: {} from ip: {} has been blocked", request.getRequestURI(), ip);
                    throw new RequestForbiddenException();
                }
            }
        }

        if (whiteSet != null && whiteSet.contains(ip)) {
            RequestInfoHolder.setInWhiteList(true);
        }

        filterChain.doFilter(request, response);
        RequestInfoHolder.removeInWhiteList();
    }
}
