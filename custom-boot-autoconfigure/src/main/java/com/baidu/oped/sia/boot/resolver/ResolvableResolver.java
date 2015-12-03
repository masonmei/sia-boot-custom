package com.baidu.oped.sia.boot.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by mason on 11/16/15.
 */
public interface ResolvableResolver {

    Resolvable resolve(MethodParameter parameter, ModelAndViewContainer mavContainer,
                       NativeWebRequest webRequest, WebDataBinderFactory binderFactory);

}
