package com.baidu.oped.sia.boot.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Categories of system process result.
 *
 * @author mason
 */
public enum SystemCode {
    OK(1),
    INVALID_PARAMETER(2),
    INVALID_PARAMETER_VALUE(3),
    INTERNAL_ERROR(4),
    EXCEED_MAX_RETURN_DATA_POINTS(5),
    EXCEED_MAX_QUERY_DATA_POINTS(6),
    AUTHENTICATION_ERROR(7),
    AUTHORIZATION_ERROR(8),
    ACCESS_DENIED(9),
    RESOURCE_NOT_EXIST(10),
    INCORRECT_CONFIGURATION(11);

    private static Map<Integer, SystemCode> hash = new HashMap<>();
    private final int value;

    SystemCode(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        String packageName = getClass().getPackage().getName();
        String className = getClass().getName();
        className = className.replace("$", ".");
        return className.substring(packageName.length() + 1, className.length()) + "." + name();
    }
}
