package com.baidu.oped.sia.boot.bcm.iam.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Iam auth response.
 *
 * @author mason
 */
public class IamAuthResponse implements Serializable {
    private Token token;

    public Catalog getCatelogByType(String type) {
        return token.getCatalogByType(type);
    }

    /**
     * Get the public endpoint of given type.
     *
     * @param type service type
     * @return endpoint
     */
    public String getPublicEndpoint(String type) {
        Catalog catalog = getCatelogByType(type);
        if (null != catalog && null != catalog.getEndpoints()) {
            for (Endpoint endpoint : catalog.getEndpoints()) {
                if (null != endpoint.getIface() && endpoint.getIface().equalsIgnoreCase("public")) {
                    return endpoint.getUrl();
                }
            }
        }
        return null;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Token entity.
     */
    public static class Token {
        private List<Catalog> catalog = new ArrayList<>();
        private User user;
        @JsonProperty(value = "expires_at")
        private Date expiresAt;
        @JsonProperty(value = "issued_at")
        private Date issuedAt;

        public List<Catalog> getCatalog() {
            return catalog;
        }

        public void setCatalog(List<Catalog> catalog) {
            this.catalog = catalog;
        }

        /**
         * Find the catalog type.
         *
         * @param type service type
         * @return catalog
         */
        public Catalog getCatalogByType(String type) {
            if (catalog != null) {
                for (Catalog cat : catalog) {
                    if (cat.getType() != null && cat.getType().equals(type)) {
                        return cat;
                    }
                }
            }
            return null;
        }

        public Date getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(Date expiresAt) {
            this.expiresAt = expiresAt;
        }

        public Date getIssuedAt() {
            return issuedAt;
        }

        public void setIssuedAt(Date issuedAt) {
            this.issuedAt = issuedAt;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class User {
        private String id;
        private String name;
        private Domain domain;

        public Domain getDomain() {
            return domain;
        }

        public void setDomain(Domain domain) {
            this.domain = domain;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Catalog {
        private String id;
        private String type;
        private List<Endpoint> endpoints;

        public List<Endpoint> getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(List<Endpoint> endpoints) {
            this.endpoints = endpoints;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Endpoint {
        private String id;
        private String region;
        @JsonProperty(value = "interface")
        private String iface;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIface() {
            return iface;
        }

        public void setIface(String iface) {
            this.iface = iface;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Domain {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
