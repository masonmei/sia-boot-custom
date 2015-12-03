package com.baidu.oped.sia.boot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mason on 11/18/15.
 */
@RestController
public class TestController {

    @RequestMapping("test/{user}")
    public String test(@PathVariable String user){
        return "test " + user;
    }
}
