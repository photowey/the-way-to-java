package io.github.photowey.proguard.in.action.controller;

import io.github.photowey.proguard.in.action.domain.entity.Hello;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code HelloController}
 *
 * @author photowey
 * @date 2023/11/12
 * @since 1.0.0
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping("/world/{age}")
    public ResponseEntity<Hello> hello(
            @RequestParam("name") String name, @PathVariable("age") Integer age) {
        return new ResponseEntity<>(populateHello(name), HttpStatus.OK);
    }

    private static Hello populateHello(String name) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("hello", "world");
        map.put("tom", "jerry");

        Hello hello = Hello.builder()
                .variableString(name)
                .variableInt(10)
                .variableLong(System.currentTimeMillis())
                .variableObject(map)
                .build();

        return hello;
    }
}
