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
package com.photowey.spring.in.action.dynamic.selector;

import com.photowey.spring.in.action.dynamic.handler.DynamicInvokeHandler;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * {@code DynamicInjectedAutoConfigurationImportSelector}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
public class DynamicInjectedAutoConfigurationImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 将 DynamicInvokeHandler 实现集-注入到 Spring 容器
        List<String> dynamicInvokeHandlerClassNames = SpringFactoriesLoader.loadFactoryNames(DynamicInvokeHandler.class, ClassUtils.getDefaultClassLoader());
        return StringUtils.toStringArray(dynamicInvokeHandlerClassNames);
    }
}
