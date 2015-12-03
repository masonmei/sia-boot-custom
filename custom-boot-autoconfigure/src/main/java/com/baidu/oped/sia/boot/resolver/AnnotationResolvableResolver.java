package com.baidu.oped.sia.boot.resolver;

import com.baidu.oped.sia.boot.resolver.annotation.FromHeader;
import com.baidu.oped.sia.boot.resolver.annotation.FromQuery;
import com.baidu.oped.sia.boot.utils.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by mason on 11/16/15.
 */
public class AnnotationResolvableResolver implements ResolvableResolver {
    private static final Logger LOG = LoggerFactory.getLogger(AnnotationResolvableResolver.class);

    public AnnotationResolvableResolver() {
    }

    @Override
    public Resolvable resolve(MethodParameter parameter, ModelAndViewContainer mavContainer,
                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Class<?> parameterType = parameter.getParameterType();
        if (!Resolvable.class.isAssignableFrom(parameterType)) {
            LOG.debug("{} is not a resolvable class", parameterType);
            return null;
        }

        if (parameterType.getSuperclass() != Resolvable.class) {
            LOG.debug("only support direct sub class of resolvable");
            return null;
        }

        try {

            final Resolvable t = (Resolvable) parameterType.newInstance();
            Field[] fields = ResolverUtils.getDeclaredFields(parameterType);
            for (Field field : fields) {
                try {
                    ResolverUtils.makeAccessible(field);
                    Object value = populateValueFromRequest(field, webRequest);
                    field.set(t, value);
                } catch (IllegalAccessException e) {
                    LOG.warn("Please checking weather the field {} is static or final", field);
                }
            }

            return t;
        } catch (InstantiationException e) {
            LOG.warn("Cannot create instance of class {}", parameterType);
        } catch (IllegalAccessException e) {
            LOG.warn("Not found null args constructor or is not accessible");
        }
        return null;
    }


    private Object populateValueFromRequest(Field field, NativeWebRequest request) {
        FromQuery fromQueryAnnotation = field.getAnnotation(FromQuery.class);
        if (fromQueryAnnotation != null) {
            String name = fromQueryAnnotation.name();
            if (name == null) {
                name = field.getName();
            }
            if (field.getType().isArray()) {
                String[] parameterValues = request.getParameterValues(name);
                return ArrayUtils.convertStringArrayToPrimitiveArr(field.getType(), parameterValues);
            } else {
                String parameter = request.getParameter(name);
                return ArrayUtils.convertStringToPrimitive(field.getType(), parameter);
            }
        }

        FromHeader fromHeaderAnnotation = field.getAnnotation(FromHeader.class);
        if (fromHeaderAnnotation != null) {
            String name = fromHeaderAnnotation.name();
            if (name == null) {
                name = field.getName();
            }
            if (field.getType().isArray()) {
                String[] parameterValues = request.getHeaderValues(name);
                return ArrayUtils.convertStringArrayToPrimitiveArr(field.getType(), parameterValues);
            } else {
                String parameter = request.getHeader(name);
                return ArrayUtils.convertStringToPrimitive(field.getType(), parameter);
            }
        }

        return null;
    }
}
