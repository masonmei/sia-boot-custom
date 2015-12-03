package com.baidu.oped.sia.boot.utils;

import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mason on 11/18/15.
 */
public abstract class ArrayUtils {

    public static final List<Class<?>> WRAPPER_CLASS = Arrays.<Class<?>>asList(
            Boolean.class,
            Byte.class,
            Character.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class
    );

    /**
     * Convert String array to the primitive array
     *
     * @param type   array type
     * @param values values in String
     * @return the object represent an array
     */
    public static Object convertStringArrayToPrimitiveArr(Class<?> type, String[] values) {
        if (!type.isArray()) {
            throw new UnsupportedOperationException("type must be an array.");
        }
        Class<?> componentType = type.getComponentType();
        Object result = Array.newInstance(componentType, values.length);
        for (int i = 0; i < values.length; i++) {
            Object convert = convertStringToPrimitive(componentType, values[i]);
            Array.set(result, i, convert);
        }
        return result;
    }

    /**
     * Convert String to the primitive types
     *
     * @param type  the primitive class type
     * @param value the value in string
     * @return the primitive value in wrapper type
     */
    public static Object convertStringToPrimitive(Class<?> type, String value) {
        if (!(isString(type) || isPrimitiveOrWrapperClass(type))) {
            throw new UnsupportedOperationException("Not support yet");
        }

        if (type == String.class) {
            return value;
        }

        if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(value);
        }

        if (type == Byte.class || type == byte.class) {
            return Byte.parseByte(value);
        }

        if (type == Short.class || type == short.class) {
            return Short.parseShort(value);
        }

        if (type == Character.class || type == char.class) {
            return value.length() != 0 ? value.charAt(0) : Character.MIN_VALUE;
        }

        if (type == Integer.class || type == int.class) {
            return Integer.parseInt(value);
        }

        if (type == Long.class || type == long.class) {
            return Long.parseLong(value);
        }

        if (type == Float.class || type == float.class) {
            return Float.parseFloat(value);
        }

        if (type == Double.class || type == double.class) {
            return Double.parseDouble(value);
        }

        throw new UnsupportedOperationException("Not support type");
    }

    /**
     * Check if the class is String
     *
     * @param type the class type to check
     * @return
     */
    public static boolean isString(Class<?> type) {
        Assert.notNull(type, "type must not be null while checking is String");
        return type == String.class;
    }

    /**
     * Check if the class is a primitive or wrapper type
     *
     * @param type
     * @return
     */
    public static boolean isPrimitiveOrWrapperClass(Class<?> type) {
        Assert.notNull(type, "type must not be null while checking is Primitive or Wrapper");
        return type.isPrimitive() || isWrapperClass(type);
    }

    /**
     * Check if the given type is an wrapper class
     *
     * @param type
     * @return
     */
    public static boolean isWrapperClass(Class<?> type) {
        Assert.notNull(type, "type must not be null while checking is  Wrapper");
        return WRAPPER_CLASS.contains(type);
    }

}
