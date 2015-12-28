package com.baidu.oped.sia.boot.iam.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.util.CollectionUtils;

/**
 * Created by mason on 12/16/15.
 */
public class BccVmDetailResponse {
    private Server server;

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

        public List<String> getFloatingIps() {
            List<String> floatingIps = new ArrayList<>();
            if (!CollectionUtils.isEmpty(addresses)) {
                for (List<Address> address : addresses.values()) {
                    if (!CollectionUtils.isEmpty(address)) {
                        for (Address addr : address) {
                            if (addr.getType().equalsIgnoreCase("floating")) {
                                floatingIps.add(addr.getAddr());
                            }
                        }
                    }
                }
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
