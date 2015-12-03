/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.profiling;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by meidongxu on 7/6/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Profiling {

}