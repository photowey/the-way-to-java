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
package com.photowey.spring.project.infras.in.action.service;

import com.photowey.spring.project.infras.in.action.App;
import com.photowey.spring.project.infras.in.action.LocalTest;
import com.photowey.spring.project.infras.in.action.core.domain.entity.Employee;
import io.github.photowey.spring.infras.core.context.ApplicationContextHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

/**
 * {@code EmployeeServiceTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/13
 */
@SpringBootTest(classes = App.class)
class EmployeeServiceTest extends LocalTest {

    @Test
    void testHolder() {
        ConfigurableApplicationContext applicationContext = ApplicationContextHolder.INSTANCE.applicationContext();
        Assertions.assertNotNull(applicationContext);
    }

    @Test
    void testRepository() {
        Assertions.assertNotNull(this.employeeRepository);
    }

    @Test
    void testAdd() {
        LocalDateTime now = LocalDateTime.now();
        Employee employee = Employee.builder()
                .id(System.currentTimeMillis())
                .organizationId(System.currentTimeMillis())
                .employeeNo(uuid() + "." + "random")
                .employeeName(uuid() + "." + "random")
                .createdBy(System.currentTimeMillis())
                .updatedBy(System.currentTimeMillis())
                .createdAt(now)
                .updateAt(now)
                .deleted(0)
                .build();

        this.employeeService.add(employee);
    }
}