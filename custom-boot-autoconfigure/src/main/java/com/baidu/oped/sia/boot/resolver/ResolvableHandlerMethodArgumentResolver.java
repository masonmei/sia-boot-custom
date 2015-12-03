package com.baidu.oped.sia.boot.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by mason on 11/16/15.
 */
public class ResolvableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private ResolvableResolver resolver;

    public ResolvableHandlerMethodArgumentResolver(ResolvableResolver resolver) {
        this(resolver, null);
    }

    public ResolvableHandlerMethodArgumentResolver(ResolvableResolver resolver, String currentAttributeName) {
        this.resolver = resolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return Resolvable.class.isAssignableFrom(parameterType) && parameterType.getSuperclass() == Resolvable.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return resolver.resolve(parameter, mavContainer, webRequest, binderFactory);
    }

}
