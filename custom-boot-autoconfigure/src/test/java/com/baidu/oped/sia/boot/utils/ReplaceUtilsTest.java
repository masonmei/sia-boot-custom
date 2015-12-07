package com.baidu.oped.sia.boot.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by mason on 12/2/15.
 */
public class ReplaceUtilsTest {

    @Test
    public void testReplacePattern() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("123", "543");
        String s = ReplaceUtils.replacePattern("/test/{123}/fs", params);
        assertEquals("/test/543/fs", s);


        s = ReplaceUtils.replacePattern("/test/{123}/fs/{324}/", params);
        assertEquals("/test/543/fs/{324}/", s);

        s = ReplaceUtils.replacePattern("/test/{12/3}/fs/{324}/", params);
        params.put("12/3", "123");
        assertEquals("/test/{12/3}/fs/{324}/", s);
    }
}