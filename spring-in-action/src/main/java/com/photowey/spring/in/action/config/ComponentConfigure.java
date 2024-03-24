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
package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.component.ComponentBean;
import com.photowey.spring.in.action.component.ComponentBeanRef;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * {@code ComponentConfigure}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
@Component
public class ComponentConfigure {

    @Bean
    public ComponentBean componentBean() {
        return new ComponentBean();
    }

    @Bean
    public ComponentBeanRef componentBeanRef(ComponentBean componentBean) {
        return new ComponentBeanRef(componentBean);
    }

    /*@Bean
    public HeadRequestMappingHandlerMapping headRequestMappingHandlerMapping() {
        return new HeadRequestMappingHandlerMapping();
    }*/
}
