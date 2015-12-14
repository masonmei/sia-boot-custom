package com.baidu.oped.sia.boot.utils;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * Ip Utils
 * <p>
 * Created by mason on 10/29/15.
 */
public abstract class IpV4Utils {
    public static final String PATTERN_255 = "(?:25[0-5]|2[0-4][0-9]|[1]?[0-9][0-9]?)";
    public static final Pattern PATTERN_IPV4 = Pattern.compile("^(?:" + PATTERN_255 + "\\.){3}" + PATTERN_255 + "$");

    public static String longToIp(long longIp) {
        int octet3 = (int) ((longIp >> 24) % 256);
        int octet2 = (int) ((longIp >> 16) % 256);
        int octet1 = (int) ((longIp >> 8) % 256);
        int octet0 = (int) ((longIp) % 256);

        return String.format("%d.%d.%d.%d", octet3, octet2, octet1, octet0);
    }

    public static boolean isPrivate(String ip) {
        long longIp = ipToLong(ip);
        return (longIp >= ipToLong("10.0.0.0") && longIp <= ipToLong("10.255.255.255"))
                || (longIp >= ipToLong("172.16.0.0") && longIp <= ipToLong("172.31.255.255"))
                || longIp >= ipToLong("192.168.0.0") && longIp <= ipToLong("192.168.255.255");
    }

    public static long ipToLong(String ip) {
        String[] octets = ip.split("\\.");
        return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
                + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }

    public static boolean isValid(String ip) {
        return PATTERN_IPV4.matcher(ip).matches();
    }

    public static boolean isInRanges(List<String> ipOrRanges, String clientIp) {
        for (String ipOrRange : ipOrRanges) {
            if (isInRange(ipOrRange, clientIp)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInRange(String ipOrRange, String clientIP) {
        int indexOfSlash = ipOrRange.indexOf("/");
        if (indexOfSlash < 0) {
            return ipOrRange.equals(clientIP);
        } else {
            String[] split = ipOrRange.split("/", 2);
            Assert.state(split.length == 2, "Invalid ip range configuration");
            long checker = ipToLong(split[0]);
            long range = 32 - Long.parseLong(split[1]);
            long clientIpInLong = ipToLong(clientIP);
            return (checker >> range) == (clientIpInLong >> range);
        }

    }

}
