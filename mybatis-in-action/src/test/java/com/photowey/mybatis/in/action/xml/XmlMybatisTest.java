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
import com.photowey.mybatis.in.action.query.EmployeeQuery;
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
 * {@code XmlMybatisTest}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
class XmlMybatisTest {

    @Test
    @Deprecated
    void testXmlMybatis() {
        String mybatisConfig = "mybatis-config-sample.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(mybatisConfig)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // id = 1457238442743197697L
            // @see com.photowey.mybatis.in.action.repository.EmployeeRepositoryTest.testCustomFindById
            this.selectBySqlSession(sqlSession);
            this.selectByMapper(sqlSession);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Deprecated
    void testXmlMybatis$() {
        String mybatisConfig = "mybatis-config-sample.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(mybatisConfig)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // employeeNo = 2021109527
            // @see com.photowey.mybatis.in.action.repository.EmployeeRepositoryTest.testCustomFindById
            EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);
            EmployeeModel employeeModel = employeeRepository.findByEmployeeNo$("2021109527");

            this.doAssert(employeeModel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Deprecated
    void testXmlMybatisDynamicSQL() {
        String mybatisConfig = "mybatis-config-sample.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(mybatisConfig)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // employeeNo = 2021109527
            // @see com.photowey.mybatis.in.action.repository.EmployeeRepositoryTest.testCustomFindById
            EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);

            EmployeeQuery query1 = new EmployeeQuery();
            query1.setOrgId(2021109527L);
            List<EmployeeModel> employeeModels1 = employeeRepository.findAllByDynamicSQL(query1);
            employeeModels1.forEach(this::doAssert);

            EmployeeQuery query2 = new EmployeeQuery();
            query2.setOrgName("宇宙漫游者");
            List<EmployeeModel> employeeModels2 = employeeRepository.findAllByDynamicSQL(query2);
            employeeModels2.forEach(this::doAssert);

            EmployeeQuery query3 = new EmployeeQuery();
            query3.setOrgName("宇宙漫游者");
            query3.setEmployeeNo("2021109527");
            List<EmployeeModel> employeeModels3 = employeeRepository.findAllByDynamicSQL(query3);
            employeeModels3.forEach(this::doAssert);

            EmployeeQuery query4 = new EmployeeQuery();
            query4.setOrgId(2021109527L);
            query4.setOrgName("宇宙漫游者");
            query4.setEmployeeNo("2021109527");
            List<EmployeeModel> employeeModels4 = employeeRepository.findAllByDynamicSQL(query4);
            employeeModels4.forEach(this::doAssert);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Deprecated
    void testXmlMybatisDynamicSQLByMap() {
        String mybatisConfig = "mybatis-config-sample.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(mybatisConfig)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // employeeNo = 2021109527
            // @see com.photowey.mybatis.in.action.repository.EmployeeRepositoryTest.testCustomFindById
            EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);

            Map<String, Object> query1 = new HashMap<>(6);
            query1.put("orgId", 2021109527L);
            List<EmployeeModel> employeeModels1 = employeeRepository.findAllByDynamicSQLMap(query1);
            employeeModels1.forEach(this::doAssert);

            Map<String, Object> query2 = new HashMap<>(6);
            query2.put("orgName", "宇宙漫游者");
            List<EmployeeModel> employeeModels2 = employeeRepository.findAllByDynamicSQLMap(query2);
            employeeModels2.forEach(this::doAssert);

            Map<String, Object> query3 = new HashMap<>(6);
            query3.put("orgName", "宇宙漫游者");
            query3.put("employeeNo", "2021109527");
            List<EmployeeModel> employeeModels3 = employeeRepository.findAllByDynamicSQLMap(query3);
            employeeModels3.forEach(this::doAssert);

            Map<String, Object> query4 = new HashMap<>(6);
            query4.put("orgId", 2021109527L);
            query4.put("orgName", "宇宙漫游者");
            query4.put("employeeNo", "2021109527");
            List<EmployeeModel> employeeModels4 = employeeRepository.findAllByDynamicSQLMap(query4);
            employeeModels4.forEach(this::doAssert);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectByMapper(SqlSession sqlSession) {
        EmployeeRepository employeeRepository = sqlSession.getMapper(EmployeeRepository.class);
        EmployeeModel employeeModel = employeeRepository.findById(1457238442743197697L);

        this.doAssert(employeeModel);
    }

    private void selectBySqlSession(SqlSession sqlSession) {
        EmployeeModel employeeModelBySession = sqlSession.selectOne(
                "com.photowey.mybatis.in.action.repository.EmployeeRepository.findById", 1457238442743197697L);
        this.doAssert(employeeModelBySession);
    }

    private void doAssert(EmployeeModel employeeModel) {
        Assertions.assertNotNull(employeeModel);
        Assertions.assertEquals("2021109527", employeeModel.getEmployeeNo());
        Assertions.assertEquals(2021109527L, employeeModel.getOrgId());
        Assertions.assertEquals("宇宙漫游者", employeeModel.getOrgName());
        Assertions.assertEquals(1024, employeeModel.getOrderNo());
        Assertions.assertEquals(1, employeeModel.getStatus());
        Assertions.assertEquals("我是备注", employeeModel.getRemark());
    }
}
