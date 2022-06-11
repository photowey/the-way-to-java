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
package com.photowey.mybatis.in.action.repository.dynamic;

import com.photowey.mybatis.in.action.domain.entity.Employee;
import com.photowey.mybatis.in.action.mybatis.dynamic.kernel.criteria.Criteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code EmployeeDynamicRepositoryTest}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@SpringBootTest
class EmployeeDynamicRepositoryTest {

    @Autowired
    private EmployeeDynamicRepository employeeDynamicRepository;

    @Test
    void testDynamicInsert() {
        Employee employeeInsert = this.populateEmployee();
        employeeInsert.setId(1457238442743197698L);
        this.employeeDynamicRepository.dynamicInsert(employeeInsert);

        Employee employeeExample = new Employee();
        employeeExample.setId(employeeInsert.getId());
        List<Employee> employees = this.employeeDynamicRepository.dynamicSelect(employeeExample);
        Assertions.assertTrue(employees.size() > 0);
    }

    @Test
    void testDynamicDelete() {
        Employee employeeInsert = this.populateEmployee();
        employeeInsert.setId(1457238442743197699L);
        this.employeeDynamicRepository.dynamicInsert(employeeInsert);

        Employee employeeExample = new Employee();
        employeeExample.setId(employeeInsert.getId());
        List<Employee> employees = this.employeeDynamicRepository.dynamicSelect(employeeExample);
        Assertions.assertTrue(employees.size() > 0);

        // 执行删除
        this.employeeDynamicRepository.dynamicDelete(employeeExample);

        List<Employee> employeeDeletes = this.employeeDynamicRepository.dynamicSelect(employeeExample);
        Assertions.assertEquals(0, employeeDeletes.size());
    }

    @Test
    void testDynamicUpdate() {
        // id = 1457238442743197697
        Employee employeeExample = new Employee();
        employeeExample.setId(1457238442743197697L);
        List<Employee> employees = this.employeeDynamicRepository.dynamicSelect(employeeExample);
        Assertions.assertNotNull(employees);
        for (Employee employee : employees) {
            Employee employeeExampleUpdate = new Employee();
            employeeExampleUpdate.setId(employee.getId());
            employeeExampleUpdate.setRemark("我是备注修改");
            this.employeeDynamicRepository.dynamicUpdate(employeeExampleUpdate);
            List<Employee> employeeUpdates = this.employeeDynamicRepository.dynamicSelect(employeeExample);
            Assertions.assertNotNull(employeeUpdates);
            for (Employee employeeUpdate : employeeUpdates) {
                Assertions.assertEquals("我是备注修改", employeeUpdate.getRemark());
            }
        }
    }

    @Test
    void testDynamicSelect() {
        Employee employeeExample = new Employee();
        employeeExample.setId(1457238442743197698L);
        List<Employee> employees = this.employeeDynamicRepository.dynamicSelect(employeeExample);
        Assertions.assertTrue(employees.size() > 0);
    }

    @Test
    void testDynamicCriteria() {
        Employee employeeExample = new Employee();
        employeeExample.setId(1457238442743197698L);
        Criteria criteria = Criteria.create()
                .eq("id", 1457238442743197698L)
                .and()
                .eq("org_name", "探戈玫瑰");
        List<Employee> employees = this.employeeDynamicRepository.dynamicCriteria(criteria);
        Assertions.assertTrue(employees.size() > 0);
    }

    private Employee populateEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeNo("2021108848");
        employee.setOrgId(2021108848L);
        employee.setOrgName("探戈玫瑰");
        employee.setOrderNo(2048);
        employee.setStatus(1);
        employee.setRemark("我是备注伍六七");
        employee.setCreateTime(LocalDateTime.now());
        employee.setCreateBy(20211088489527L);
        employee.setModifiedTime(LocalDateTime.now());
        employee.setUpdateBy(20211088489527L);
        employee.setDeleted(0);

        return employee;
    }
}