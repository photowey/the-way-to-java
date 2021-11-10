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
package com.photowey.mybatis.in.action.xml;

import com.photowey.mybatis.in.action.domain.model.EmployeeModel;
import com.photowey.mybatis.in.action.page.Page;
import com.photowey.mybatis.in.action.page.PageUtils;
import com.photowey.mybatis.in.action.repository.EmployeeRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code XmlMybatisPluginTest}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
class XmlMybatisPluginTest {

    @Test
    @Deprecated
    void testMybatisPlugin() {
        String mybatisConfig = "mybatis-config-sample.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(mybatisConfig)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            this.selectByMapper(sqlSession);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectByMapper(SqlSession sqlSession) {
        EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);
        Map<String, Object> query = new HashMap<>(6);
        query.put("orgId", 2021109527L);
        query.put("orgName", "宇宙漫游者");
        query.put("employeeNo", "2021109527");
        PageUtils.walk(1, 2);
        // SELECT * FROM sys_employee e WHERE e.org_id = ? AND e.org_name = ? AND e.employee_no = ? LIMIT ?, ?
        // 2021109527(Long), 宇宙漫游者(String), 2021109527(String), 0(Integer), 2(Integer)
        Page<EmployeeModel> page = employeeRepository.findPageByDynamicSQLMap(query);
        List<EmployeeModel> employeeModels = page.getData();
        // 这个断言-不太准确 - 测试库里面-符合要求的只有一条
        this.doAssert(employeeModels);

        PageUtils.walk(2, 2);
        // SELECT * FROM sys_employee e WHERE e.org_id = ? AND e.org_name = ? AND e.employee_no = ? LIMIT ?, ?
        // 2021109527(Long), 宇宙漫游者(String), 2021109527(String), 2(Integer), 2(Integer)
        Page<EmployeeModel> page1 = employeeRepository.findPageByDynamicSQLMap(query);
        List<EmployeeModel> employeeModels1 = page1.getData();
        Assertions.assertEquals(0, employeeModels1.size());
    }

    private void doAssert(List<EmployeeModel> employeeModels) {
        Assertions.assertEquals(1, employeeModels.size());
    }
}
