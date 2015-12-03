/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.oped.sia.boot.validate.clientsource;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by mason on 7/14/15.
 */
public class ActionClientSourceChecker implements ClientSourceChecker {

    private final String patten;

    public ActionClientSourceChecker(String patten) {
        Assert.hasLength(patten, "Pattern may not be empty.");
        this.patten = patten;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class == clazz;
    }

    @Override
    public boolean needValidateSource(Object target) {
        return checkPattern((String) target);
    }

    protected boolean checkPattern(String target) {
        return !StringUtils.isEmpty(target) && target.contains(patten);
    }
}
