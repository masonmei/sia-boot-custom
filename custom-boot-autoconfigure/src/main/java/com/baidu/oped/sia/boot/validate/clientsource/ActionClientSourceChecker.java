package com.baidu.oped.sia.boot.validate.clientsource;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Client source checker for bcm alarm action checking.
 *
 * @author mason
 */
public class ActionClientSourceChecker implements ClientSourceChecker {

    private final String patten;

    public ActionClientSourceChecker(String patten) {
        Assert.hasLength(patten, "Pattern may not be empty.");
        this.patten = patten;
    }

    @Override
    public boolean needValidateSource(Object target) {
        return checkPattern((String) target);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class == clazz;
    }

    protected boolean checkPattern(String target) {
        return !StringUtils.isEmpty(target) && target.contains(patten);
    }
}
