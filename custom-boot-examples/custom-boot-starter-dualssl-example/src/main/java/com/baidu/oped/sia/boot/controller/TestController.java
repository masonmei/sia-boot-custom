package com.baidu.oped.sia.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mason on 11/19/15.
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String test() {
        return "test";
    }
}
