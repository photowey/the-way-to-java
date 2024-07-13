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

import com.photowey.spring.project.infras.in.action.core.domain.entity.Employee;
import io.github.photowey.spring.infras.core.getter.BeanFactoryGetter;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * {@code EmployeeService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/13
 */
public interface EmployeeService extends BeanFactoryAware, BeanFactoryGetter {

    void add(Employee employee);

    Employee load(Long employeeId);
}

