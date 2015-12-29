package com.baidu.oped.sia.boot.bcm.rewrite;

import static com.baidu.oped.sia.boot.bcm.iam.internal.AgentRequestRewriteParameterResolverAgent.USER_ID;

import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import com.baidu.oped.sia.boot.utils.ReplaceUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mason on 12/2/15.
 */
public class AgentUriRewriteFiler extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AgentUriRewriteFiler.class);

    private AgentUriRewriteParameterResolver parameterResolver;

    private RewriteContext rewriteContext;

    public void setParameterResolver(AgentUriRewriteParameterResolver parameterResolver) {
        this.parameterResolver = parameterResolver;
    }

    public void setRewriteContext(RewriteContext rewriteContext) {
        this.rewriteContext = rewriteContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Map<String, String> params = parameterResolver.resolve(request);
        String servletPath = request.getServletPath();

        if (params.isEmpty() && !params.containsKey(USER_ID)) {
            LOG.warn("resolve userId params failed.");
            return;
        }

        if (servletPath.startsWith(rewriteContext.getPattern())) {
            String rewrite = rewriteContext.getRewrite();
            rewrite = ReplaceUtils.replacePattern(rewrite, params);

            servletPath = servletPath.replace(rewriteContext.getPattern(), rewrite);

            RequestInfoHolder.setCurrentUser(params.get(USER_ID));
            RequestInfoHolder.setIgnoreAuth(true);
            filterChain.doFilter(new PathRewriteRequestWrapper(request, servletPath), response);
            RequestInfoHolder.removeIgnoreAuth();
        }
    }
}
