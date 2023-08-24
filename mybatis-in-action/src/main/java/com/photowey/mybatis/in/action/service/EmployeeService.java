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
package com.photowey.mybatis.in.action.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photowey.mybatis.in.action.domain.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code EmployeeService}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
public interface EmployeeService extends IService<Employee> {

    void handleRequiredSave();

    void handleNestedSave();

    TxStatus add(Employee employee);

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class TxStatus implements Serializable {
        private int status;
    }
}
