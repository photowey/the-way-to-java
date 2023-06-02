/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.mybatis.in.action.plugin.chatgpt.mybatis.scanner;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * {@code MybatisMapperScanner}
 *
 * @author photowey
 * @date 2023/06/03
 * @since 1.0.0
 */
@Component
public class MybatisMapperScanner implements BeanDefinitionRegistryPostProcessor {

    private final ConfigurableApplicationContext applicationContext;
    private final MapperScannerConfigurer mapperScannerConfigurer;
    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Autowired
    public MybatisMapperScanner(ConfigurableApplicationContext applicationContext, MapperScannerConfigurer mapperScannerConfigurer) {
        this.applicationContext = applicationContext;
        this.mapperScannerConfigurer = mapperScannerConfigurer;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    public void refreshMappers() {
        // 关闭 MyBatis 的 MapperScannerConfigurer
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory"); // 设置 SqlSessionFactory 的 Bean名称
        mapperScannerConfigurer.setBasePackage("com.example.mapper"); // 设置 Mapper 接口所在的包路径

        // 执行刷新操作
        mapperScannerConfigurer.postProcessBeanDefinitionRegistry(this.beanDefinitionRegistry);
        mapperScannerConfigurer.postProcessBeanFactory(applicationContext.getBeanFactory());
    }
}