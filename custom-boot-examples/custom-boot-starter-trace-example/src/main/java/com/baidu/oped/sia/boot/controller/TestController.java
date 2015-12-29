package com.baidu.oped.sia.boot.controller;

import com.baidu.oped.sia.boot.common.RequestInfoHolder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mason on 11/29/15.
 */
@RestController
public class TestController {
    @RequestMapping(value = {"home"},
            method = RequestMethod.GET)
    public String home() {
        return String.format("Trace home with id %s start at %s", RequestInfoHolder.traceId(),
                RequestInfoHolder.traceTimestamp());
    }
}
