package com.baidu.oped.sia.boot.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mason on 12/2/15.
 */
public class ReplaceUtils {
    private static final String PATTERN_STRING = "\\{[^\\{\\\\/}]*\\}";

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
