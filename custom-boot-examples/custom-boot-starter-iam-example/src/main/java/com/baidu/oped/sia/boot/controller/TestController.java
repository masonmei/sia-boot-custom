package com.baidu.oped.sia.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mason on 12/2/15.
 */
@RestController
@RequestMapping("/site")
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "test";
    }
}
