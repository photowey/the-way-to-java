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
package com.photowey.spring.in.action.hello.processor;

import com.photowey.spring.in.action.hello.annotation.JungleService;
import com.photowey.spring.in.action.hello.factorybean.JungleFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code JungleBeanDefinitionRegistryPostProcessor}
 *
 * @author photowey
 * @date 2023/06/04
 * @since 1.0.0
 */
public class JungleBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private BeanDefinitionRegistry registry;
    private ConfigurableListableBeanFactory beanFactory;

    private ResourceLoader resourceLoader;
    private ResourcePatternResolver resourcePatternResolver;
    private MetadataReaderFactory metadataReaderFactory;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(this.resourceLoader);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;

        this.scan();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private void scan() {
        // TODO 修改 basePackages
        Set<Class<?>> beanClasses = this.scanPackages("com.photowey.spring.in.action.hello");
        for (Class<?> clazz : beanClasses) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);

            definition.setBeanClass(JungleFactoryBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            definition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

            BeanNameGenerator beanNameGenerator = this.beanFactory.getBean(BeanNameGenerator.class);
            String beanName = beanNameGenerator.generateBeanName(definition, this.registry);
            this.registry.registerBeanDefinition(beanName, definition);
        }
    }

    private Set<Class<?>> scanPackages(String... basePackages) {
        Set<Class<?>> sets = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + this.resolveBasePackage(basePackage) + "/" + DEFAULT_RESOURCE_PATTERN;

            try {
                Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
                List<Resource> readableResources = Stream.of(resources).filter(Resource::isReadable).collect(Collectors.toList());
                for (Resource readableResource : readableResources) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(readableResource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    try {
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(JungleService.class)) {
                            sets.add(clazz);
                        }
                    } catch (Exception ignored) {
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(String.format("path: [%s] not found", packageSearchPath));
            }
        }

        return sets;
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(this.propertyResolver().resolvePlaceholders(basePackage));
    }

    private PropertyResolver propertyResolver() {
        return this.beanFactory.getBean(Environment.class);
    }
}
