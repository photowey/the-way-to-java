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
package com.photowey.druid.in.action.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.photowey.druid.in.action.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * {@code StatController}
 *
 * @author photowey
 * @date 2022/02/20
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class StatController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * SatHello
     *
     * @param name 名称
     * @return {@code Hello ${name}}
     * @see * http://localhost:9527/hello/photowey
     */
    @GetMapping("/{name}")
    public ResponseEntity<String> sayHello(@PathVariable("name") String name) {
        for (long i = 0L; i < 10L; i++) {
            this.employeeRepository.findById(i);
            if (i % 2L == 0L) {
                this.employeeRepository.findById(1457238442743197697L);
            }
        }

        return new ResponseEntity<>(String.format("Hello %s!", name), HttpStatus.OK);
    }

    /**
     * @see * http://localhost:9527/hello/stat/druid
     */
    @GetMapping("/stat/druid")
    public ResponseEntity<List<Map<String, Object>>> druidStat() {
        List<Map<String, Object>> stat = DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
        return new ResponseEntity<>(stat, HttpStatus.OK);
    }
}
