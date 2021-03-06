package com.baidu.oped.sia.boot.controller;

import com.baidu.oped.sia.boot.exception.RequestForbiddenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mason on 11/17/15.
 */
@RestController
public class TestController {

    @RequestMapping("exception")
    public String exception() {
        throw new RequestForbiddenException();
    }

    @Autowired

    @RequestMapping("test")
    public String test() {
        return "test" + LocaleContextHolder.getLocale().getDisplayName();
    }
}
