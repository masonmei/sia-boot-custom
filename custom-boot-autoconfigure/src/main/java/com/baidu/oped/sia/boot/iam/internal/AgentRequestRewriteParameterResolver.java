package com.baidu.oped.sia.boot.iam.internal;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import com.baidu.oped.cloudwatch.business.common.rewrite.AgentUriRewriteParameterResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by mason on 12/16/15.
 */
@Component
public class AgentRequestRewriteParameterResolver implements AgentUriRewriteParameterResolver {
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
