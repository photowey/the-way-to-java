package com.photowey.spring.project.infras.in.action;

import com.photowey.spring.project.infras.in.action.repository.EmployeeRepository;
import com.photowey.spring.project.infras.in.action.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * {@code LocalTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/08/08
 */
public abstract class LocalTest {

    @Autowired
    protected EmployeeService employeeService;
    @Autowired(required = false)
    protected EmployeeRepository employeeRepository;

    protected String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}