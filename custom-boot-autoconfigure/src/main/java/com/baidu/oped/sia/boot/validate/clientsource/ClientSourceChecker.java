/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

/**
 * Client Source checker.
 *
 * @author mason
 */
public interface ClientSourceChecker {

    /**
     * Checking if the target need to be validate with checker.
     *
     * @param target the validating target
     * @return need validate or not
     */
    boolean needValidateSource(Object target);

    /**
     * Checking if this checker support the checking.
     *
     * @param clazz the checker target value type
     * @return check support result
     */
    boolean supports(Class<?> clazz);

}
