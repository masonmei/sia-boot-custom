package com.baidu.oped.sia.boot.resolver.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation the value should populate from query.
 *
 * @author mason
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FromQuery {

    /**
     * Query field name.
     *
     * @return field name
     */
    String name() default "";
}
