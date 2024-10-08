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
package com.photowey.webservice.core.in.action.annotation;

import com.photowey.webservice.core.in.action.config.XmlObjectMapperConfigure;
import com.photowey.webservice.core.in.action.injector.ApplicationContextInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableWebserviceCode}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {
    XmlObjectMapperConfigure.class,
    EnableWebserviceCore.EnableWebserviceCoreConfigure.class,
})
public @interface EnableWebserviceCore {

    @ComponentScan("com.photowey.webservice.core.in.action")
    class EnableWebserviceCoreConfigure {

        @Bean
        public ApplicationContextInjector applicationContextInjector() {
            return new ApplicationContextInjector();
        }
    }
}
