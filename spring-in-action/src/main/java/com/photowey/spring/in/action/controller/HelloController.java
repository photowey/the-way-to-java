package com.photowey.spring.in.action.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code HelloController}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * @see * http://localhost:7923/hello/greeting?name=photowey
     */
    @GetMapping("/greeting")
    public ResponseEntity<String> sayHello(@RequestParam("name") String name) {
        return new ResponseEntity<>(String.format("Say hello to %s,this is spring-in-action simple-controller~", name), HttpStatus.OK);
    }
}
