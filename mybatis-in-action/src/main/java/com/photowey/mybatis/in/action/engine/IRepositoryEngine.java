package com.photowey.mybatis.in.action.engine;

import com.photowey.mybatis.in.action.repository.EmployeeRepository;

/**
 * {@code IRepositoryEngine}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
public interface IRepositoryEngine extends IEngine {

    EmployeeRepository employeeRepository();
}
