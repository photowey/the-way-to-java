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
package com.photowey.spring.project.infras.in.action.service.impl;

import com.photowey.spring.project.infras.in.action.core.domain.entity.Employee;
import com.photowey.spring.project.infras.in.action.loader.EmployeeLoader;
import com.photowey.spring.project.infras.in.action.property.InfrasCoreProperties;
import com.photowey.spring.project.infras.in.action.repository.EmployeeRepository;
import com.photowey.spring.project.infras.in.action.service.EmployeeService;
import io.github.photowey.spring.infras.core.context.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@code EmployeeServiceImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/13
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private ConfigurableListableBeanFactory beanFactory;

    private final InfrasCoreProperties props;

    public EmployeeServiceImpl(InfrasCoreProperties props) {this.props = props;}

    @Override
    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void add(Employee employee) {
        log.info("core: prepare add employee to database, id:[{}]", employee.getId());

        ConfigurableApplicationContext applicationContext = ApplicationContextHolder.INSTANCE.applicationContext();
        EmployeeRepository repository = applicationContext.getBean(EmployeeRepository.class);
        repository.save(employee);
    }

    @Override
    public Employee load(Long employeeId) {
        Map<String, EmployeeLoader> beans = this.listableBeanFactory().getBeansOfType(EmployeeLoader.class);
        ArrayList<EmployeeLoader> employeeLoaders = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(employeeLoaders);

        for (EmployeeLoader employeeLoader : employeeLoaders) {
            if (employeeLoader.supports(this.props.getCache().getLoader())) {
                return employeeLoader.load(employeeId);
            }
        }

        throw new UnsupportedOperationException("Unreachable here.");
    }
}