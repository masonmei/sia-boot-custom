package com.baidu.oped.sia.boot.controller;

import com.baidu.oped.sia.boot.exception.RequestForbiddenException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test Controller.
 *
 * @author mason
 */
@RestController
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("exception")
    public String exception() {
        throw new RequestForbiddenException();
    }

    @Autowired

    @RequestMapping("test")
    public String test() {
        LOG.debug("test to test");
        return "test";
    }
}
