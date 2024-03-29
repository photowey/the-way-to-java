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

import com.photowey.spring.in.action.provider.SwaggerHandlerProvider;
import com.photowey.spring.in.action.provider.SwaggerHandlerProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * {@code SwaggerWebResourceConfigure}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Configuration
public class SwaggerWebResourceConfigure implements WebMvcConfigurer {

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

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(this.responseBodyConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
