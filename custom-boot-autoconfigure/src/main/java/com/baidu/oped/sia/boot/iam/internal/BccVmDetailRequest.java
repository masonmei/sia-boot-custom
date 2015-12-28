package com.baidu.oped.sia.boot.iam.internal;

import static java.lang.String.format;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.util.Assert;

/**
 * Created by mason on 12/16/15.
 */
public class BccVmDetailRequest {
    public static final String BCC_AUTH_HEADER_NAME = "X-Auth-Token";
    private static final String BCC_VM_DETAIL_URL_TEMPLATE = "%s/servers/%s";
    private String endpoint;
    private String vmUuid;
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRequestTarget() {
        Assert.hasLength(endpoint, "UserName must have length.");
        Assert.hasLength(vmUuid, "Password must have length.");
        Assert.hasLength(authToken, "Project Id must have length.");

        return format(BCC_VM_DETAIL_URL_TEMPLATE, endpoint, vmUuid);
    }

    public String getVmUuid() {
        return vmUuid;
    }

    public void setVmUuid(String vmUuid) {
        this.vmUuid = vmUuid;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(endpoint, vmUuid, authToken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BccVmDetailRequest that = (BccVmDetailRequest) o;
        return Objects.equal(endpoint, that.endpoint)
                && Objects.equal(vmUuid, that.vmUuid)
                && Objects.equal(authToken, that.authToken);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("endpoint", endpoint)
                .add("vmUuid", vmUuid)
                .add("authToken", authToken)
                .toString();
    }
}
