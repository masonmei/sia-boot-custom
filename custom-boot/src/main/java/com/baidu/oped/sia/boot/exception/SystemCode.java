package com.baidu.oped.sia.boot.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

/**
 * Categories of system process result.
 *
 * @author mason
 */
public enum SystemCode {
    OK(1, HttpStatus.OK, HttpStatus.CREATED, HttpStatus.ACCEPTED, HttpStatus.NO_CONTENT),
    INVALID_PARAMETER(2, HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER_VALUE(3, HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(4, HttpStatus.INTERNAL_SERVER_ERROR),
    EXCEED_MAX_RETURN_DATA_POINTS(5, HttpStatus.BAD_REQUEST),
    EXCEED_MAX_QUERY_DATA_POINTS(6, HttpStatus.BAD_REQUEST),
    AUTHENTICATION_ERROR(7, HttpStatus.FORBIDDEN),
    AUTHORIZATION_ERROR(8, HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(9, HttpStatus.TOO_MANY_REQUESTS),
    RESOURCE_NOT_EXIST(10, HttpStatus.NOT_FOUND),
    INCORRECT_CONFIGURATION(11, HttpStatus.INTERNAL_SERVER_ERROR),
    REQUEST_ERROR(12, HttpStatus.BAD_REQUEST, HttpStatus.UNSUPPORTED_MEDIA_TYPE, HttpStatus.METHOD_NOT_ALLOWED);

    private final int value;
    private final HttpStatus[] statusCode;

    SystemCode(int value, HttpStatus... statusCode) {
        this.value = value;
        this.statusCode = statusCode;
    }

    public int getValue() {
        return value;
    }

    public HttpStatus[] getStatusCode() {
        return Arrays.copyOf(statusCode, statusCode.length);
    }

    @Override
    public String toString() {
        String packageName = getClass().getPackage().getName();
        String className = getClass().getName();
        className = className.replace("$", ".");
        return className.substring(packageName.length() + 1, className.length()) + "." + name();
    }
}
