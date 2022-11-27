package com.photowey.spring.boot.v3.aot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code HomeController}
 *
 * @author photowey
 * @date 2022/11/27
 * @since 1.0.0
 */
@RestController
public class HomeController {

    @GetMapping("/")
    String home() {
        return "Hello World!";
    }
}
