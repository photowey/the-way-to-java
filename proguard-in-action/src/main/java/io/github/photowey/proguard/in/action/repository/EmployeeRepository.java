package io.github.photowey.proguard.in.action.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.photowey.proguard.in.action.domain.entity.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * {@code EmployeeRepository}
 *
 * @author photowey
 * @date 2023/11/12
 * @since 1.0.0
 */
public interface EmployeeRepository extends BaseMapper<Employee> {

    Employee findById(@Param("id") Long id);
}
