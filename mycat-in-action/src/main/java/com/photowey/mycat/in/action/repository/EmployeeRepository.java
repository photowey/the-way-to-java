/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.mycat.in.action.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.photowey.mycat.in.action.domain.entity.Employee;
import com.photowey.mycat.in.action.domain.model.EmployeeModel;
import com.photowey.mycat.in.action.query.EmployeeQuery;
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

    /**
     * 自定义插件-分页
     *
     * @param query 查询条件装
     * @return
     */
    Page<EmployeeModel> findPageByDynamicSQLMap(Map<String, Object> query);

}