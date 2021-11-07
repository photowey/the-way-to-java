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
package com.photowey.mybatis.in.action.repository;

import com.photowey.mybatis.in.action.domain.entity.Employee;
import com.photowey.mybatis.in.action.domain.model.EmployeeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * {@code EmployeeRepositoryTest}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testHello() {
        Employee employee = this.employeeRepository.selectById(0L);
        Assertions.assertNull(employee);
    }

    @Test
    void testCustomFindById() {
        Employee employee = this.populateEmployee();
        this.employeeRepository.insert(employee);

        EmployeeModel employeeModel = this.employeeRepository.findById(employee.getId());
        this.doAssert(employeeModel);
    }

    private void doAssert(EmployeeModel employeeModel) {
        Assertions.assertNotNull(employeeModel);
        Assertions.assertEquals("2021109527", employeeModel.getEmployeeNo());
        Assertions.assertEquals(2021109527L, employeeModel.getOrgId());
        Assertions.assertEquals("宇宙漫游者", employeeModel.getOrgName());
        Assertions.assertEquals(1024, employeeModel.getOrderNo());
        Assertions.assertEquals(1, employeeModel.getStatus());
        Assertions.assertEquals("我是备注", employeeModel.getRemark());
    }

    private Employee populateEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeNo("2021109527");
        employee.setOrgId(2021109527L);
        employee.setOrgName("宇宙漫游者");
        employee.setOrderNo(1024);
        employee.setStatus(1);
        employee.setRemark("我是备注");
        employee.setCreateTime(LocalDateTime.now());
        employee.setCreateBy(20211095278848L);
        employee.setModifiedTime(LocalDateTime.now());
        employee.setUpdateBy(20211095278848L);
        employee.setDeleted(0);

        return employee;
    }

}