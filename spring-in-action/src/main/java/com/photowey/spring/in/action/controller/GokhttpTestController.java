/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * {@code GokhttpTestController}
 *
 * @author photowey
 * @date 2022/02/01
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/gokhttp")
@Api(value = "Gokhttp接口", tags = {"Gokhttp测试接口"})
public class GokhttpTestController {

    /**
     * POST :/hello
     *
     * @param payload {@link HelloPayload}
     * @return {@link ResponseEntity<   HelloBody   >}
     * @since 1.0.0
     */
    @PostMapping("/hello")
    @ApiOperation("POST-请求")
    public ResponseEntity<HelloBody> handleQuery(@Validated @RequestBody @ApiParam HelloPayload payload) {
        log.info("the request body is:\n{}", JSON.toJSONString(payload));
        return new ResponseEntity<>(new HelloBody(1L, "photowey", 18), HttpStatus.OK);
    }

    /**
     * GET :/hello
     *
     * @param name
     * @param age
     * @return
     */
    @GetMapping("/hello")
    @ApiOperation("GET-请求")
    public ResponseEntity<HelloBody> handleQuery(String name, Integer age) {
        log.info("the request parameters, name is:[{}],age:[{}]", name, age);
        return new ResponseEntity<>(new HelloBody(1L, "photowey", 18), HttpStatus.OK);
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HelloBody implements Serializable {
        private static final long serialVersionUID = 2999038197573108304L;
        private Long id;
        private String name;
        private Integer age;
    }
}
