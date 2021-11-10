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
package com.photowey.spring.in.action.dynamic.registrar;

import com.photowey.spring.in.action.dynamic.prosessor.DynamicInjectedAnnotationBeanPostProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * {@code DynamicInjectedBeanPostProcessorRegistrar}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
public class DynamicInjectedBeanPostProcessorRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Log logger = LogFactory.getLog(DynamicInjectedBeanPostProcessorRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        registerBeans(registry, DynamicInjectedAnnotationBeanPostProcessor.class);
    }

    public static void registerBeans(BeanDefinitionRegistry registry, Class<?>... annotatedClasses) {

        if (ObjectUtils.isEmpty(annotatedClasses)) {
            return;
        }

        boolean debugEnabled = logger.isDebugEnabled();

        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);

        if (debugEnabled) {
            logger.debug(registry.getClass().getSimpleName() + " will register annotated classes : " + Arrays.asList(annotatedClasses) + " .");
        }

        reader.register(annotatedClasses);

    }
}
