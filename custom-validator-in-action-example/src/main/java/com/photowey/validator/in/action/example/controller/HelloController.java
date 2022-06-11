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
package com.photowey.validator.in.action.example.controller;

import com.photowey.validator.in.action.example.assembler.HelloAssembler;
import com.photowey.validator.in.action.example.domain.dto.HelloDTO;
import com.photowey.validator.in.action.example.domain.entity.Hello;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code HelloController}
 *
 * @author photowey
 * @date 2022/02/24
 * @since 1.0.0
 */
@RestController
@RequestMapping("/hello")
@Api(tags = "Hello模块", description = "Hello接口")
public class HelloController {

    @Autowired
    private HelloAssembler helloAssembler;

    @PostMapping("/whisper")
    @ApiOperation(value = "说悄悄话")
    public ResponseEntity<Hello> sayHello(@Validated @RequestBody HelloDTO dto) {
        Hello hello = this.helloAssembler.toEntity(dto);
        return new ResponseEntity<>(hello, HttpStatus.OK);
    }

}
