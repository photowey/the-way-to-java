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
package com.photowey.swagger.provider.ext.annotation;

import com.photowey.swagger.provider.ext.SwaggerHandlerProvider;
import com.photowey.swagger.provider.ext.SwaggerHandlerProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableSwaggerProviderExt}
 *
 * @author photowey
 * @date 2022/11/11
 * @since 1.0.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {EnableSwaggerProviderExt.EnableSwaggerProviderExtConfigure.class})
public @interface EnableSwaggerProviderExt {

    @Configuration
    class EnableSwaggerProviderExtConfigure {

        /**
         * 解决高版本 {@code Spring Boot} {@code Swagger} 启动空指针问题
         *
         * <pre>
         * org.springframework.context.ApplicationContextException: Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
         * 	at org.springframework.context.support.DefaultLifecycleProcessor.doStart(DefaultLifecycleProcessor.java:181)
         * ...
         * Caused by: java.lang.NullPointerException: null
         * 	at springfox.documentation.spring.web.WebMvcPatternsRequestConditionWrapper.getPatterns(WebMvcPatternsRequestConditionWrapper.java:56)
         * 	at springfox.documentation.RequestHandler.sortedPaths(RequestHandler.java:113)
         * 	...
         * 	... 15 common frames omitted
         * </pre>
         *
         * @return {@link SwaggerHandlerProvider}
         */
        @Bean
        public static SwaggerHandlerProvider swaggerHandlerProvider() {
            return new SwaggerHandlerProviderImpl();
        }
    }
}
