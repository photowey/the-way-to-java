package com.photowey.mybatis.in.action.engine;

import com.photowey.mybatis.in.action.repository.EmployeeRepository;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code RepositoryEngine}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class RepositoryEngine implements IRepositoryEngine {

    @Getter
    @Autowired
    private EmployeeRepository employeeRepository;
}
