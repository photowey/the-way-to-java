package com.photowey.mybatis.in.action.engine;

import com.photowey.mybatis.in.action.service.EmployeeService;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code ServiceEngine}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class ServiceEngine implements IServiceEngine {

    @Getter
    @Autowired
    private EmployeeService employeeService;
}
