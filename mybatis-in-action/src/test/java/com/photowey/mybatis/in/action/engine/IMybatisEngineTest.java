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
package com.photowey.mybatis.in.action.engine;

import com.photowey.mybatis.in.action.Mybatis;
import com.photowey.mybatis.in.action.domain.entity.Employee;
import com.photowey.mybatis.in.action.repository.EmployeeRepository;
import com.photowey.mybatis.in.action.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * {@code IMybatisEngineTest}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@SpringBootTest(classes = {Mybatis.class, IMybatisEngineTest.HelloConfigure.class})
class IMybatisEngineTest {

    @Autowired
    private IMybatisEngine mybatisEngine;

    @Test
    void testHello() {
        Map<String, EmployeeRepository> beans = this.mybatisEngine.beanFactory().getBeansOfType(EmployeeRepository.class);
        beans.forEach((k, v) -> {
            Employee employee = v.selectById(0L);
            Assertions.assertNull(employee);
        });
    }

    @Test
    void testHello2() {
        ApplicationContext applicationContext = this.mybatisEngine.applicationContext();
        applicationContext.publishEvent(new HelloApplicationEvent("Hello ApplicationContext~"));
    }

    @Test
    void testHello3() {
        Environment environment = this.mybatisEngine.environment();
        String app = environment.getProperty("spring.application.name");
        Assertions.assertEquals("mybatis-in-action", app);
    }

    @Test
    void testRepositoryEngine() {
        IRepositoryEngine repositoryEngine = this.mybatisEngine.repositoryEngine();
        EmployeeRepository employeeRepository = repositoryEngine.employeeRepository();
        Employee employee = employeeRepository.selectById(0L);
        Assertions.assertNull(employee);
    }

    @Test
    void testServiceEngine() {
        IServiceEngine serviceEngine = this.mybatisEngine.serviceEngine();
        EmployeeService employeeService = serviceEngine.employeeService();
        Employee employee = employeeService.getById(0L);
        Assertions.assertNull(employee);
    }

    // =========================================

    @Configuration
    public static class HelloConfigure {

        @Bean
        public HelloApplicationListener helloApplicationListener() {
            return new HelloApplicationListener();
        }

    }

    @Slf4j
    public static class HelloApplicationListener implements ApplicationListener<HelloApplicationEvent> {

        @Override
        public void onApplicationEvent(HelloApplicationEvent event) {
            log.info("listen the HelloApplicationEvent source is:{}", event.getSource());
        }
    }

    public static class HelloApplicationEvent extends ApplicationEvent {
        public HelloApplicationEvent(Object source) {
            super(source);
        }
    }
}