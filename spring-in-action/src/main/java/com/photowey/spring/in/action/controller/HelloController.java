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
package com.photowey.spring.in.action.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.photowey.spring.in.action.serializer.LongListJsonSerializer;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * {@code HelloController}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@RestController
@RequestMapping("/hello")
@Api(value = "Hello模块", tags = {"Hello接口"})
public class HelloController {

    /**
     * @see * http://localhost:7923/hello/greeting?name=photowey
     */
    @GetMapping("/greeting")
    public ResponseEntity<String> sayHello(@RequestParam("name") String name) {
        return new ResponseEntity<>(String.format("Say hello to %s,this is spring-in-action simple-controller~", name), HttpStatus.OK);
    }

    /**
     * @see * http://localhost:7923/hello/json/serializer
     */
    @GetMapping("/json/serializer")
    public ResponseEntity<Student> jsonSerializer() {
        return new ResponseEntity<>(new Student(), HttpStatus.OK);
    }

    public static class Student implements Serializable {

        private static final long serialVersionUID = -5380128820527461238L;

        @JsonSerialize(using = LongListJsonSerializer.class)
        private List<Long> ids = Lists.newArrayList(5380128820527461237L, 5380128820527461238L);

        public List<Long> getIds() {
            return ids;
        }

        public List<Long> ids() {
            return ids;
        }

        public void setIds(List<Long> ids) {
            this.ids = ids;
        }

        public Student ids(List<Long> ids) {
            this.ids = ids;
            return this;
        }
    }
}
