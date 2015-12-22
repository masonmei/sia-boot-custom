package com.baidu.oped.sia.boot.access.label;

import com.baidu.oped.sia.boot.common.DelegateHolder;
import com.baidu.oped.sia.boot.common.RequestInfoHolder;

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
public class LabelFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LabelFilter.class);

    private final DelegateHolder<Label> label;

    public LabelFilter(DelegateHolder<Label> label) {
        Assert.notNull(label, "Label Delegate Holder must not be null.");
        this.label = label;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        LOG.debug("filter request: {} from {} with LabelFilter", request.getRequestURI(), ip);

        List<String> whiteSet = label.getContext().getWhites();

        if (whiteSet != null && whiteSet.contains(ip)) {
            LOG.debug("request: {} from {} has in the white list.", request.getRequestURI(), ip);
            RequestInfoHolder.setInWhiteList(true);
        }

        filterChain.doFilter(request, response);
        RequestInfoHolder.removeInWhiteList();
    }
}
