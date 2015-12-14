package com.baidu.oped.sia.boot.resolver;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * Resolver Utils.
 *
 * @author mason
 */
public abstract class ResolverUtils {
    /**
     * Cache for {@link Class#getDeclaredFields()}, allowing for fast iteration.
     */
    private static final Map<Class<?>, Field[]> DECLARED_FIELDS_CACHE = new ConcurrentReferenceHashMap<>(256);

    /**
     * Get all the modifiable fields of the given.
     *
     * @param clazz the given Resolvable class
     * @return the modifiable fields
     * @throws SecurityException
     */
    public static <T> Field[] getDeclaredFields(Class<T> clazz) throws SecurityException {
        Assert.isAssignable(Resolvable.class, clazz, "Only support sub class of Resolvable");
        Field[] result = DECLARED_FIELDS_CACHE.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredFields();
            result = filter(result);
            DECLARED_FIELDS_CACHE.put(clazz, result);
        }

        return result;
    }

    private static Field[] filter(Field[] fields) {
        if (fields == null) {
            return new Field[0];
        }

        List<Field> fieldList = new ArrayList<>(fields.length);
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (isFinal(field.getModifiers()) || isStatic(modifiers)) {
                continue;
            }
            fieldList.add(field);
        }
        return fieldList.toArray(new Field[fieldList.size()]);
    }

    /**
     * Make the given field accessible, explicitly setting it accessible if
     * necessary. The {@code setAccessible(true)} method is only called
     * when actually necessary, to avoid unnecessary conflicts with a JVM
     * SecurityManager (if active).
     *
     * @param field the field to make accessible
     * @see java.lang.reflect.Field#setAccessible
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

}
