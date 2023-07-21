package com.flab.goodchoiceapi.coupon.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//    @Value("${test}")
    private String test;

    @GetMapping("/api/test")
    public String testt() {
        System.out.println(test);
        return test;
    }
}
