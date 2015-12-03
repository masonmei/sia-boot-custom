/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

/**
 * Created by mason on 7/14/15.
 */
public interface ClientSourceChecker {

    boolean supports(Class<?> clazz);

    boolean needValidateSource(Object target);

}
