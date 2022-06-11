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
package com.photowey.dubbo.consumer.in.action.setup;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.PropertySource;

/**
 * {@code AnnotationSetup}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@EnableDubbo(scanBasePackages = "com.photowey")
@PropertySource(value = "dubbo.properties")
public class AnnotationSetup {

    /**
     * {@link EnableDubbo} 导入 {@link com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig} 注解和
     * {@link com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan} 注解
     * <p>
     * {@link com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig} 的作用
     * 1.创建标签配置类,并 注册到 Spring 容器里面
     * 2.对标签配置类进行数据绑定
     * 3.其中绑定的数据来源 {@link org.springframework.core.env.Environment}, 之一就是 {@code dubbo.properties}
     * <p>
     * {@link com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan} 的作用
     * 负责将 {@link com.alibaba.dubbo.config.annotation.Service} 和 {@link com.alibaba.dubbo.config.annotation.Reference}
     * 两注解的支持类,注册到 Spring 容器里面。
     * <p>
     * {@link com.alibaba.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor} 的作用
     * 1.自定义 {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner} 的子类
     * {@link com.alibaba.dubbo.config.spring.context.annotation.DubboClassPathBeanDefinitionScanner}
     * <p>
     * 2.对 {@code dubbo} 中的 {@link com.alibaba.dubbo.config.annotation.Service} 扫描,
     * 把 {@literal @}{@link com.alibaba.dubbo.config.annotation.Service} 注解的类 变成
     * {@link org.springframework.beans.factory.config.BeanDefinition} 对象
     * <p>
     * 3.循环扫描到的 {@link org.springframework.beans.factory.config.BeanDefinition} 列表 创建
     * {@link com.alibaba.dubbo.config.spring.ServiceBean} 的
     * {@link org.springframework.beans.factory.config.BeanDefinition} 对象
     * <p>
     * 4.对 {@link com.alibaba.dubbo.config.spring.ServiceBean} 的 {@link org.springframework.beans.factory.config.BeanDefinition} 对象进行属性填充
     * <p>
     * 5.注册
     * <p>
     * {@link com.alibaba.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor}
     */
    private void init() {
        // do nothing
    }
}
