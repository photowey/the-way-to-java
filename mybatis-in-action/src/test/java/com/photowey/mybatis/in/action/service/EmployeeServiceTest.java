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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.photowey.mybatis.in.action.Mybatis;
import com.photowey.mybatis.in.action.domain.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code EmployeeServiceTest}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@SpringBootTest(classes = Mybatis.class)
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testHello() {
        Employee employee = this.employeeService.getById(0L);
        Assertions.assertNull(employee);
    }

    @Test
    void testTargetSQL() {
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<Employee>()
                .select(Employee::getId, Employee::getEmployeeNo)
                .eq(Employee::getOrgName, "github").or().eq(Employee::getEmployeeNo, "9527");

        Assertions.assertEquals("(ORG_NAME = ? OR EMPLOYEE_NO = ?)", qw.getTargetSql());
    }

    @Test
    void testAddEmployeeForRegisterSynchronization() {
        // 在测试数据库中真实存在
        Long employeeId = 1457238442743197697L;

        Employee employee = this.employeeService.getById(employeeId);
        employee.setId(null);
        employee.setEmployeeNo("2023039527");
        employee.setOrderNo(4096);
        employee.setRemark("我是事务备注");
        EmployeeService.TxStatus txStatus = this.employeeService.add(employee);

        Assertions.assertEquals(1, txStatus.getStatus());
    }
}