package com.photowey.mybatis.in.action.service;

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
@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testHello() {
        Employee employee = this.employeeService.getById(0L);
        Assertions.assertNull(employee);
    }

}