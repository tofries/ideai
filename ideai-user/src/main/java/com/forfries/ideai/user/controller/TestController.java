package com.forfries.ideai.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/user/test")
    public String test() {
        return "test";
    }
}
