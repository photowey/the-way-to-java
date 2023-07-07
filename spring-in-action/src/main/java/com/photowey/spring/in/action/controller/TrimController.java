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
package com.photowey.spring.in.action.controller;

import com.photowey.spring.in.action.annotation.TrimField;
import com.photowey.spring.in.action.annotation.TrimMethod;
import com.photowey.spring.in.action.domain.model.TrimModel;
import com.photowey.spring.in.action.trim.annotation.TrimSpace;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * {@code TrimController}
 *
 * @author photowey
 * @date 2023/07/06
 * @since 1.0.0
 */
@RestController
@RequestMapping("/trim")
@Api(value = "参数修改", tags = {"String参数修改"})
public class TrimController {

    @GetMapping("/trimspace")
    public ResponseEntity<String> trimSpace(@TrimSpace @RequestParam("name") String name) {
        return new ResponseEntity<>(String.format("hello %s~", name), HttpStatus.OK);
    }

    @TrimMethod
    @GetMapping("/get")
    public ResponseEntity<String> get(@TrimField @RequestParam("name") String name) {
        return new ResponseEntity<>(String.format("hello %s~", name), HttpStatus.OK);
    }

    @TrimMethod
    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody @ApiParam @Validated StudentPayload payload) {
        return new ResponseEntity<>(String.format("hello %s~", payload.getName()), HttpStatus.OK);
    }

    @TrimMethod
    @GetMapping("/get/query")
    public ResponseEntity<String> queryGet(StudentQuery payload) {
        return new ResponseEntity<>(String.format("hello %s~", payload.getName()), HttpStatus.OK);
    }

    @Data
    public static class StudentPayload implements Serializable, TrimModel {

        private static final long serialVersionUID = 3067875834364794883L;

        @TrimField
        private String name;
    }

    @Data
    public static class StudentQuery implements Serializable, TrimModel {

        private static final long serialVersionUID = 3067875834364794883L;

        @TrimField
        private String name;
    }
}
