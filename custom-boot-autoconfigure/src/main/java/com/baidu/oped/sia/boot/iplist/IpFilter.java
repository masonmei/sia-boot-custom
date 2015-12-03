package com.baidu.oped.sia.boot.iplist;

import com.baidu.oped.sia.boot.common.FileWatcher;
import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.utils.IpV4Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mason on 10/29/15.
 */
public class IpFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(IpFilter.class);

    private final FileWatcher<IpHolder> fileWatcher;

    public IpFilter(FileWatcher<IpHolder> fileWatcher) {
        Assert.notNull(fileWatcher, "IpHolder watcher must not be null.");
        this.fileWatcher = fileWatcher;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Initialing IpFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String ip = servletRequest.getRemoteAddr();

        List<String> blackSet = fileWatcher.getIpListHolder().getDeny();
        List<String> whiteSet = fileWatcher.getIpListHolder().getAllow();

        if (blackSet != null) {
            for (String blackRange : blackSet) {
                if (IpV4Utils.isInRange(blackRange, ip)) {
                    return;
                }
            }
        }

        if (whiteSet != null && whiteSet.contains(ip)) {
            RequestInfoHolder.setThreadIgnoreAuth(true);
            filterChain.doFilter(servletRequest, servletResponse);
            RequestInfoHolder.removeThreadIgnoreAuth();
        } else {
            RequestInfoHolder.setThreadIgnoreAuth(false);
            filterChain.doFilter(servletRequest, servletResponse);
            RequestInfoHolder.removeThreadIgnoreAuth();
        }
    }

    @Override
    public void destroy() {
        LOG.info("Destroying IpFilter");
    }
}
