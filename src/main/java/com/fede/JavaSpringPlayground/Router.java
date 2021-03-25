package com.fede.JavaSpringPlayground;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fede")
public class Router {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
