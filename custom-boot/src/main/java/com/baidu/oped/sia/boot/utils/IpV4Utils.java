package com.baidu.oped.sia.boot.utils;

import org.springframework.util.Assert;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Ip Utils.
 *
 * @author mason
 */
public abstract class IpV4Utils {
    public static final String PATTERN_255 = "(?:25[0-5]|2[0-4][0-9]|[1]?[0-9][0-9]?)";
    public static final Pattern PATTERN_IPV4 = Pattern.compile("^(?:" + PATTERN_255 + "\\.){3}" + PATTERN_255 + "$");

    /**
     * Check if the client ip in the given ip ranges.
     *
     * @param ipOrRanges the checking ranges
     * @param clientIp   the client ip
     * @return checking result
     */
    public static boolean isInRanges(List<String> ipOrRanges, String clientIp) {
        for (String ipOrRange : ipOrRanges) {
            if (isInRange(ipOrRange, clientIp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the client ip in the given ip range.
     *
     * @param ipOrRange the checking range
     * @param clientIp  the client ip
     * @return checking result
     */
    public static boolean isInRange(String ipOrRange, String clientIp) {
        int indexOfSlash = ipOrRange.indexOf("/");
        if (indexOfSlash < 0) {
            return ipOrRange.equals(clientIp);
        } else {
            String[] split = ipOrRange.split("/", 2);
            Assert.state(split.length == 2, "Invalid ip range configuration");
            long checker = ipToLong(split[0]);
            long range = 32 - Long.parseLong(split[1]);
            long clientIpInLong = ipToLong(clientIp);
            return (checker >> range) == (clientIpInLong >> range);
        }

    }

    /**
     * Convert ipv4 string to long.
     *
     * @param ip ipv4 address
     * @return the long present ip address
     */
    public static long ipToLong(String ip) {
        String[] octets = ip.split("\\.");
        return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
                + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }

    /**
     * Checking if the ip is private ip.
     *
     * @param ip the checking ip
     * @return checking result
     */
    public static boolean isPrivate(String ip) {
        long longIp = ipToLong(ip);
        return (longIp >= ipToLong("10.0.0.0") && longIp <= ipToLong("10.255.255.255"))
                || (longIp >= ipToLong("172.16.0.0") && longIp <= ipToLong("172.31.255.255"))
                || longIp >= ipToLong("192.168.0.0") && longIp <= ipToLong("192.168.255.255");
    }

    /**
     * Checking if the given ip is a valid ipv4 address
     *
     * @param ip the checking ip.
     * @return checking result
     */
    public static boolean isValid(String ip) {
        return PATTERN_IPV4.matcher(ip).matches();
    }

    /**
     * Convert long to ipv4 string.
     *
     * @param longIp long present ip
     * @return ipv4 in string
     */
    public static String longToIp(long longIp) {
        int octet3 = (int) ((longIp >> 24) % 256);
        int octet2 = (int) ((longIp >> 16) % 256);
        int octet1 = (int) ((longIp >> 8) % 256);
        int octet0 = (int) ((longIp) % 256);

        return String.format("%d.%d.%d.%d", octet3, octet2, octet1, octet0);
    }

}
