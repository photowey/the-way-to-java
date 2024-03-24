/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
