package com.baidu.oped.sia.boot;

/**
 * Created by mason on 11/17/15.
 */

public class Test {

    public static final String LOWER_CASE = "a-z";
    public static final String UPPER_CASE = "A-Z";
    public static final String DIGIT = "0-9";
    public static final String CHINESE = "\u4E00-\u9fa5";
    public static final String EXTRA = "\\.\\-\\_";

    public static void main(String[] args) {
        String regex = String.format("^[%s]*$", LOWER_CASE);
        boolean result = "abc".matches(regex);
        System.out.println(result);

        regex = String.format("^[%s]*$", UPPER_CASE);
        result = "ABC".matches(regex);
        System.out.println(result);

        regex = String.format("^[%s]*$", DIGIT);
        result = "123".matches(regex);
        System.out.println(result);

        regex = String.format("^[%s]*$", EXTRA);
        result = "._-._-".matches(regex);
        System.out.println(result);
        result = "._-.1_-".matches(regex);
        System.out.println(result);

        regex = String.format("^[%s]*$", CHINESE);
        result = "国中".matches(regex);
        System.out.println(result);

        regex = String.format("^[%s%s]*$", UPPER_CASE, CHINESE);
        result = "ABC国中枯萎工".matches(regex);
        System.out.println(result);

        regex = String.format("^[%s%s]*$", UPPER_CASE, LOWER_CASE);
        result = "ABCasfd".matches(regex);
        System.out.println(result);

        regex = String.format("^([%s%s%s])*$", UPPER_CASE, LOWER_CASE, CHINESE);
        result = "工fas枯萎ABC".matches(regex);
        System.out.println(result);

        regex = String.format("^([%s%s%s])*$", CHINESE, LOWER_CASE, UPPER_CASE);
        result = "工fas枯萎ABC".matches(regex);
        System.out.println(result);


    }
}
