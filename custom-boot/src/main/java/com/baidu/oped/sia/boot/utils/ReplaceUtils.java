package com.baidu.oped.sia.boot.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace Utils for rewrite purpose.
 *
 * @author mason
 */
public class ReplaceUtils {
    private static final String PATTERN_STRING = "\\{[^\\{\\\\/}]*\\}";

    /**
     * Find the replace field from replacement and replace with the value from params map.
     *
     * @param replacement the string with replace keys.
     * @param params      params to replace.
     * @return replaced string
     */
    public static String replacePattern(String replacement, Map<String, String> params) {
        Pattern pattern = Pattern.compile(PATTERN_STRING);
        String rewrite = String.copyValueOf(replacement.toCharArray());
        Matcher matcher = pattern.matcher(rewrite);
        while (matcher.find()) {
            String group = matcher.group();
            String key = group.substring(1, group.length() - 1);
            if (params.containsKey(key)) {
                rewrite = rewrite.replace(group, params.get(key));
            }
        }
        return rewrite;
    }
}
