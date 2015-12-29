package com.baidu.oped.sia.boot.bcm.iam.internal;

import com.baidu.oped.sia.boot.bcm.rewrite.AgentUriRewriteParameterResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Iam bcm Agent uri rewrite parameter resolver.
 *
 * @author mason
 */
@Component
public class AgentRequestRewriteParameterResolverAgent implements AgentUriRewriteParameterResolver {
    public static final String USER_ID = "userId";
    private static final String AGENT_VM_HEADER_NAME = "X-BCM-VM-UUID";
    @Autowired
    private IamBccContext iamBccContext;

    @Override
    public Map<String, String> resolve(HttpServletRequest request) {
        String vmUuid = request.getHeader(AGENT_VM_HEADER_NAME);
        String remoteAddr = request.getRemoteAddr();

        String userId = getAndCache(vmUuid, remoteAddr);
        Map<String, String> map = new HashMap<>();

        if (!StringUtils.isEmpty(userId)) {
            map.put(USER_ID, userId);
        }

        return map;
    }

    private String getAndCache(final String vmUuid, final String remoteAddr) {
        return iamBccContext.getUserIdFromContext(vmUuid, remoteAddr);
    }
}
