package com.baidu.oped.sia.boot.resolver;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mason on 11/16/15.
 */
public class ResolvableHandlerInterceptor extends HandlerInterceptorAdapter {
    private final ResolvableResolver resolvableResolver;

    public ResolvableHandlerInterceptor(ResolvableResolver resolvableResolver) {
        this.resolvableResolver = resolvableResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }
}
