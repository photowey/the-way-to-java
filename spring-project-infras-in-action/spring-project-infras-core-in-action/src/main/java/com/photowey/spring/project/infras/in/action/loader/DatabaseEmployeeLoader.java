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
package com.photowey.spring.project.infras.in.action.loader;

import com.photowey.spring.project.infras.in.action.core.domain.entity.Employee;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * {@code DatabaseEmployeeLoader}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/20
 */
@Component
public class DatabaseEmployeeLoader extends AbstractEmployeeLoader {

    private static final String DATABASE_LOADER = "database";

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }

    @Override
    public boolean supports(String strategy) {
        return DATABASE_LOADER.equals(strategy);
    }

    @Override
    public Employee load(Long employeeId) {
        LocalDateTime now = LocalDateTime.now();
        return Employee.builder()
                .id(employeeId)
                .organizationId(System.currentTimeMillis())
                .employeeNo(uuid() + "." + DATABASE_LOADER)
                .employeeName(uuid() + "." + DATABASE_LOADER)
                .createdBy(System.currentTimeMillis())
                .updatedBy(System.currentTimeMillis())
                .createdAt(now)
                .updateAt(now)
                .deleted(0)
                .build();
    }
}

