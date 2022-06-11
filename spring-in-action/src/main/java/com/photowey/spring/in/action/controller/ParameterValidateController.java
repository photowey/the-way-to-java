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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * {@code ParameterValidateController}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@RestController
@RequestMapping("/valid")
@Api(value = "验证模块", tags = {"Validated接口"})
public class ParameterValidateController {

    /**
     * POST :/hello
     * 测试: 参数校验
     *
     * @param payload {@link HelloPayload}
     * @return {@link ResponseEntity<  HelloPayload  >}
     * @since 1.0.0
     */
    @PostMapping("/hello")
    @ApiOperation("测试-参数校验")
    public ResponseEntity<HelloPayload> handleQuery(@Validated @RequestBody @ApiParam HelloPayload payload) {
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @Data
    public static class HelloPayload implements Serializable {
        private static final long serialVersionUID = 2999038197573108304L;
        @NotBlank(message = "姓名不能为空")
        @Size(min = 0, max = 255, message = "姓名内容超长")
        private String name;
        @NotNull(message = "年龄不能为空")
        private Integer age;
    }
}
