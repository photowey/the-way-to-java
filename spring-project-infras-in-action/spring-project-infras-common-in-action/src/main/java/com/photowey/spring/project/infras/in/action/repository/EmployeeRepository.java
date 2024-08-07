package com.photowey.spring.project.infras.in.action.repository;

import com.photowey.spring.project.infras.in.action.core.domain.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * {@code EmployeeRepository}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/08/08
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {}

