package com.baidu.oped.sia.boot.bcm.rewrite;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Parameter Resolver for Rewrite purpose.
 *
 * @author mason
 */
public interface AgentUriRewriteParameterResolver {
    /**
     * Resolve the Parameters for Uri Rewrite Purpose.
     *
     * @param request the http servlet request.
     * @return the parameter map
     */
    Map<String, String> resolve(HttpServletRequest request);
}
