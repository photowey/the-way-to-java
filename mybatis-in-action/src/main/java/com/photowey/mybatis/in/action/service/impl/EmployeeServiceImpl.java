/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.mybatis.in.action.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.mybatis.in.action.domain.entity.Employee;
import com.photowey.mybatis.in.action.engine.IMybatisEngine;
import com.photowey.mybatis.in.action.repository.EmployeeRepository;
import com.photowey.mybatis.in.action.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

/**
 * {@code EmployeeServiceImpl}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeRepository, Employee> implements EmployeeService {

    @Autowired
    private IMybatisEngine mybatisEngine;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleRequiredSave() {
        Employee employee = this.populateEmployee();
        this.mybatisEngine.repositoryEngine().employeeRepository().insert(employee);
        if (1 != 2) {
            throw new RuntimeException("test required rollback");
        }

    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void handleNestedSave() {
        Employee employee = this.populateEmployee();
        this.mybatisEngine.repositoryEngine().employeeRepository().insert(employee);
        if (1 != 2) {
            throw new RuntimeException("test nested rollback");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TxStatus add(Employee employee) {

        TxStatus txStatus = new TxStatus(0);
        this.save(employee);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void beforeCommit(boolean readOnly) {
                log.info("before commit:{}", readOnly);
            }

            @Override
            public void beforeCompletion() {
                log.info("before completion");
            }

            @Override
            public void afterCommit() {
                log.info("after commit");
                txStatus.setStatus(1);
            }
        });

        return txStatus;
    }

    private Employee populateEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeNo("2021112527");
        employee.setOrgId(2021112527L);
        employee.setOrgName("宇宙漫游者-事务测试");
        employee.setOrderNo(1024);
        employee.setStatus(1);
        employee.setRemark("我是备注-事务测试");
        employee.setCreateTime(LocalDateTime.now());
        employee.setCreateBy(20211125278848L);
        employee.setModifiedTime(LocalDateTime.now());
        employee.setUpdateBy(20211125278848L);
        employee.setDeleted(0);

        return employee;
    }
}

