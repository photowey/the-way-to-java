package com.photowey.mybatis.in.action.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photowey.mybatis.in.action.domain.entity.Employee;
import com.photowey.mybatis.in.action.domain.model.EmployeeModel;
import com.photowey.mybatis.in.action.query.EmployeeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * {@code EmployeeRepository}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
public interface EmployeeRepository extends BaseMapper<Employee> {

  EmployeeModel findById(@Param("id") Long id);

  EmployeeModel findByEmployeeNo$(@Param("employeeNo") String employeeNo);

  List<EmployeeModel> findAllByDynamicSQL(@Param("query") EmployeeQuery query);

  List<EmployeeModel> findAllByDynamicSQLMap(Map<String, Object> query);

}