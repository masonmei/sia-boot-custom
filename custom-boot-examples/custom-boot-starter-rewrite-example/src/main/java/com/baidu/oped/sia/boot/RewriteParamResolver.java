package com.baidu.oped.sia.boot;

import com.baidu.oped.sia.boot.rewrite.UriRewriteParameterResolver;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by mason on 12/2/15.
 */
@Component
public class RewriteParamResolver implements UriRewriteParameterResolver {
    private static final String KEY = "name";

    @Override
    public Map<String, String> resolve(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        String parameter = request.getParameter(KEY);
        if (parameter != null) {
            result.put(KEY, parameter);
        } else {
            if (request.getHeader(KEY) != null) {
                result.put(KEY, request.getHeader(KEY));
            }
        }
        return result;
    }
}
