/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.provider.sentinel.in.action.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code OrganizationController}
 *
 * @author photowey
 * @date 2022/03/04
 * @since 1.0.0
 */
@RestController
@RequestMapping("/hello")
public class OrganizationController {

    /**
     * @see * http://localhost:7923/hello/greeting?name=photowey
     */
    @GetMapping("/greeting")
    public ResponseEntity<String> sayHello(@RequestParam("name") String name) {
        return new ResponseEntity<>(String.format("Say hello to %s,from provider-sentinel-in-action app", name), HttpStatus.OK);
    }
}
