package com.baidu.oped.sia.boot.iam;

import static java.lang.Boolean.TRUE;

import static com.baidu.oped.sia.boot.utils.Constrains.REMOTE_ADDRESS;
import static com.baidu.oped.sia.boot.utils.Constrains.USER;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.baidu.oped.sia.boot.common.RequestInfoHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created by mason on 12/2/15.
 */
public class IamAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(IamAuthenticationFilter.class);
    private IamManager iamManager;
    private IamAccessControl accessControl;

    public void setIamManager(IamManager iamManager) {
        this.iamManager = iamManager;
    }

    public void setAccessControl(IamAccessControl accessControl) {
        this.accessControl = accessControl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String remoteAddress = request.getRemoteAddr();

        MDC.put(REMOTE_ADDRESS, remoteAddress);

        if (!iamManager.isActive() || !accessControl.isNotIgnoredUri(request.getRequestURI())) {
            RequestInfoHolder.setIgnoreAuth(TRUE);
        }

        String user = "undefined";
        if (!RequestInfoHolder.ignoreAuth()) {
            user = iamManager.getUserFromIam(request);
        }

        MDC.put(USER, user);
        RequestInfoHolder.setCurrentUser(user);
        filterChain.doFilter(request, response);
        RequestInfoHolder.removeCurrentUser();
        MDC.remove(USER);

        MDC.remove(REMOTE_ADDRESS);
    }
}
