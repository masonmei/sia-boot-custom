package com.baidu.oped.sia.boot;

import io.swagger.annotations.ApiParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mason on 10/29/15.
 */
@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = {"api"},
            method = RequestMethod.GET)
    public String api(@ApiParam(value = "value",
            required = true) @RequestParam("value") String value) {
        return "api : " + value;
    }

    @RequestMapping(value = {"test"},
            method = RequestMethod.GET)
    public String test(@ApiParam(value = "value",
            required = true) @RequestParam("value") String value) {
        return "api : " + value;
    }
}
