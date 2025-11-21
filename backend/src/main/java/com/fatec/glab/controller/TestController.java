package com.fatec.glab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/test", produces = "text/plain")
    public String hello() {
        return "Hello World";
    }
}
