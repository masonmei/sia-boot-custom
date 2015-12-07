package com.baidu.oped.sia.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * Created by mason on 11/5/15.
 */
@RestController
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "async/callable", method = RequestMethod.GET)
    public Callable<String> asyncCallable() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return printCurrentTimestamp();
            }
        };
    }

    @RequestMapping(value = "async/result", method = RequestMethod.GET)
    public AsyncResult<String> asyncResult() {
        return new AsyncResult<>(printCurrentTimestamp());
    }

    private String printCurrentTimestamp() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            LOG.warn("sleep failed.");
        }
        return String.format("current time is : %d", System.currentTimeMillis());
    }

}
