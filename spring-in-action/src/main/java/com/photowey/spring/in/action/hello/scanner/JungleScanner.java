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
package com.photowey.spring.in.action.hello.scanner;

import com.photowey.spring.in.action.hello.annotation.JungleService;
import com.photowey.spring.in.action.hello.factorybean.JungleFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * {@code JungleScanner}
 *
 * @author photowey
 * @date 2023/06/04
 * @since 1.0.0
 */
public class JungleScanner extends ClassPathBeanDefinitionScanner {

    public JungleScanner(BeanDefinitionRegistry registry) {
        super(registry);
        this.addExcludeFilter(new AnnotationTypeFilter(JungleService.class));
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            BeanDefinition definition = beanDefinitionHolder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            definition.setBeanClassName(JungleFactoryBean.class.getName());

            try {
                definition.getConstructorArgumentValues().addGenericArgumentValue(Class.forName(beanClassName));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return beanDefinitionHolders;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // return super.isCandidateComponent(beanDefinition);
        return beanDefinition.getMetadata().isIndependent() && beanDefinition.getMetadata().isInterface();
    }
}
