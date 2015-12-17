package com.baidu.oped.sia.boot.validate.clientsource;

import com.baidu.noah.naming.BNSClient;
import com.baidu.noah.naming.BNSException;
import com.baidu.noah.naming.BNSInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of client source white label.
 *
 * @author mason
 */
public class DefaultClientSourceWhiteLabel implements ClientSourceWhiteLabel, Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ClientSourceWhiteLabel.class);

    private final ClientSourceCheckingProperties properties;
    private List<String> addressList = new ArrayList<>();
    private List<String> hostList = new ArrayList<>();

    public DefaultClientSourceWhiteLabel(ClientSourceCheckingProperties properties) {
        this.properties = properties;
        this.addressList = properties.getAddresses();
        this.hostList = properties.getHosts();
        if (!CollectionUtils.isEmpty(properties.getBnsNames())) {
            new Thread(this).start();
        }
    }

    @Override
    public boolean isWhiteAddress(String address) {
        return addressList.contains(address);
    }

    @Override
    public boolean isWhiteHost(String host) {
        return hostList.contains(host);
    }

    @Override
    public void run() {
        while (true) {
            List<String> collect = new ArrayList<>();
            for (String bnsName : properties.getBnsNames()) {
                try {
                    BNSClient client = new BNSClient();
                    List<BNSInstance> instanceByService = client.getInstanceByService(bnsName, 3000);
                    for (BNSInstance bnsInstance : instanceByService) {
                        collect.add(bnsInstance.getDottedIP());
                    }
                } catch (BNSException e) {
                    LOG.warn("getHostListByService of service:{} from BNS encounter exception: {}", bnsName,
                            e.getMessage());
                }
            }

            if (!collect.isEmpty()) {
                collect.addAll(properties.getAddresses());
                addressList = collect;
            }

            try {
                Thread.sleep(properties.getRefreshInterval() * 1000 * 60);
            } catch (Exception e) {
                LOG.debug("waiting {} minutes for refresh bns names failed.", properties.getRefreshInterval());
            }
        }
    }
}
