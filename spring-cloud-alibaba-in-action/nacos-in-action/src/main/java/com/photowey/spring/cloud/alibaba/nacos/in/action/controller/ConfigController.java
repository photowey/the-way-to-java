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
package com.photowey.spring.cloud.alibaba.nacos.in.action.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code ConfigController}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/config")
public class ConfigController {

    @NacosValue(value = "${greeting:hello world~}", autoRefreshed = true)
    private String greeting;

    /**
     * 获取刷新变量
     *
     * @return {@link ResponseEntity<String>}
     * @see * http://localhost:7923/config/refresh
     */
    @GetMapping("/refresh")
    public ResponseEntity<String> refresh() {
        // [DEBUG] com.alibaba.nacos.spring.util.parse.DefaultYamlConfigParse - Loaded 1 document from YAML resource: greeting: hello nacos-changed~
        // hello world~
        // hello nacos~
        // hello nacos-changed~
        return new ResponseEntity<>(this.greeting, HttpStatus.OK);
    }
}