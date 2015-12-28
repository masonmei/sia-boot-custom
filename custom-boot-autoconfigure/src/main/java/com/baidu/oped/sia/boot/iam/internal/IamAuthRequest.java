package com.baidu.oped.sia.boot.iam.internal;

import static java.lang.String.format;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Created by mason on 12/16/15.
 */
public class IamAuthRequest implements Serializable {
    public static final String IAM_AUTH_REQUEST_ENDPOINT_TEMPLATE = "%s://%s:%s/v3/auth/tokens";
    public static final String TOKEN_HEADER_NAME = "X-Subject-Token";
    private static final String IAM_AUTH_REQUEST_BODY_TEMPLATE = "{\n"
            + "  \"auth\": {\n"
            + "    \"identity\": {\n"
            + "      \"methods\": [\n"
            + "        \"password\"\n"
            + "      ],\n"
            + "      \"password\": {\n"
            + "        \"user\": {\n"
            + "          \"domain\": {\n"
            + "            \"name\": \"Default\"\n"
            + "          },\n"
            + "          \"name\": \"%s\",\n"
            + "          \"password\": \"%s\"\n"
            + "        }\n"
            + "      }\n"
            + "    },\n"
            + "    \"scope\": {\n"
            + "      \"project\": {\n"
            + "        \"id\": \"%s\",\n"
            + "        \"domain\": {\n"
            + "          \"id\": \"default\"\n"
            + "        }\n"
            + "      }\n"
            + "    }\n"
            + "  }\n"
            + "}";
    private String host;
    private String port;
    private String protocol;
    private String username;
    private String password;
    private String projectId;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRequestBody() {
        Assert.hasLength(username, "UserName must have length.");
        Assert.hasLength(password, "Password must have length.");
        Assert.hasLength(projectId, "Project Id must have length.");

        return format(IAM_AUTH_REQUEST_BODY_TEMPLATE, username, password, projectId);
    }

    public String getRequestEndpoint() {
        Assert.hasLength(protocol, "Protocol must have length.");
        Assert.hasLength(host, "Host must have length.");
        Assert.hasLength(port, "Port must have length.");
        return format(IAM_AUTH_REQUEST_ENDPOINT_TEMPLATE, protocol, host, port);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(host, port, protocol, username, password, projectId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IamAuthRequest request = (IamAuthRequest) o;
        return Objects.equal(host, request.host)
                && Objects.equal(port, request.port)
                && Objects.equal(protocol, request.protocol)
                && Objects.equal(username, request.username)
                && Objects.equal(password, request.password)
                && Objects.equal(projectId, request.projectId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("host", host)
                .add("port", port)
                .add("protocol", protocol)
                .add("username", username)
                .add("password", password)
                .add("projectId", projectId)
                .toString();
    }
}
