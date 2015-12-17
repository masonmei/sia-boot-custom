package com.baidu.oped.sia.boot.rewrite;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Parameter Resolver for Rewrite purpose.
 * <p>
 * Created by mason on 12/2/15.
 */
public interface UriRewriteParameterResolver {
    /**
     * Resolve the Parameters for Uri Rewrite Purpose.
     *
     * @param request the http servlet request.
     *
     * @return the parameter map
     */
    Map<String, String> resolve(HttpServletRequest request);
}
