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
package com.photowey.spring.in.action.ext.spring.web.servlet.mvc.method.annotation;

import com.photowey.spring.in.action.ext.spring.web.bind.annotation.HeadMapping;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * {@code CustomRequestMappingHandlerMapping}
 *
 * @author photowey
 * @date 2024/03/24
 * @since 1.0.0
 */
public class HeadRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    public HeadRequestMappingHandlerMapping() {
        super();
        this.setOrder(-1);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        HeadMapping headMapping = AnnotatedElementUtils.findMergedAnnotation(method, HeadMapping.class);
        if (headMapping != null) {
            RequestMappingInfo.Builder builder = RequestMappingInfo
                    .paths(super.resolveEmbeddedValuesInPatterns(headMapping.value()))
                    // fixed methods: HEAD
                    .methods(RequestMethod.HEAD)
                    .params(headMapping.params())
                    .headers(headMapping.headers())
                    .consumes(headMapping.consumes())
                    .produces(headMapping.produces())
                    .mappingName(headMapping.name());
            return builder.build();
        }

        return super.getMappingForMethod(method, handlerType);
    }
}