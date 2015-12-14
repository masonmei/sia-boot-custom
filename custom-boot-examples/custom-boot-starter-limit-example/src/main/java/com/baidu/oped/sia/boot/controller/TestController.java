package com.baidu.oped.sia.boot.controller;

import java.util.concurrent.Callable;

import com.baidu.oped.sia.boot.profiling.Profiling;

import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mason on 11/5/15.
 */
@RestController
public class TestController {

    @RequestMapping(value = "async/callable", method = RequestMethod.GET)
    public Callable<String> asyncCallable() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return printCurrentTimestamp();
            }
        };
    }

    @Profiling
    public String printCurrentTimestamp() {
        return String.format("current time is : %d", System.currentTimeMillis());
    }

    @RequestMapping(value = "async/result", method = RequestMethod.GET)
    public AsyncResult<String> asyncResult() {
        return new AsyncResult<>(printCurrentTimestamp());
    }

    @RequestMapping(value = "sync/future", method = RequestMethod.GET)
    public String syncFuture() {
        return printCurrentTimestamp();
    }

}
