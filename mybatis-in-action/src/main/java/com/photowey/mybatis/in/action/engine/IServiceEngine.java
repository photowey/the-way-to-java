package com.photowey.mybatis.in.action.engine;

import com.photowey.mybatis.in.action.service.EmployeeService;

/**
 * {@code IServiceEngine}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
public interface IServiceEngine extends IEngine {

    EmployeeService employeeService();
}
