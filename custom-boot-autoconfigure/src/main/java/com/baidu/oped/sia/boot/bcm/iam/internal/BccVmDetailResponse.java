package com.baidu.oped.sia.boot.bcm.iam.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bcc VM Details response.
 *
 * @author mason
 */
public class BccVmDetailResponse {
    private Server server;

    /**
     * Get the floating ips.
     *
     * @return floating ip
     */
    public List<String> getFloatingIps() {
        if (server != null) {
            return server.getFloatingIps();
        }
        return Collections.emptyList();
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Server entity.
     */
    public static class Server {
        private String id;
        private String status;
        private String name;
        @JsonProperty(value = "user_id")
        private String userId;
        private Map<String, List<Address>> addresses;

        public Map<String, List<Address>> getAddresses() {
            return addresses;
        }

        public void setAddresses(Map<String, List<Address>> addresses) {
            this.addresses = addresses;
        }

        /**
         * Get all the floating ips.
         *
         * @return floating ips
         */
        public List<String> getFloatingIps() {
            List<String> floatingIps = new ArrayList<>();
            if (!CollectionUtils.isEmpty(addresses)) {
                addresses.values().stream().filter(address -> !CollectionUtils.isEmpty(address)).forEach(address -> {
                    floatingIps.addAll(address.stream().filter(addr -> addr.getType().equalsIgnoreCase("floating"))
                            .map(Address::getAddr).collect(Collectors.toList()));
                });
            }
            return floatingIps;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    /**
     * Address in server.
     */
    public static class Address {
        @JsonProperty("OS-EXT-IPS-MAC:mac_addr")
        private String mac;
        private int version;
        private String addr;
        @JsonProperty("OS-EXT-IPS:type")
        private String type;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
