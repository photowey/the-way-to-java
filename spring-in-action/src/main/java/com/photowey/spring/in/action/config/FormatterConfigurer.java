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
package com.photowey.spring.in.action.config;

import com.photowey.spring.in.action.cny.factory.CnyFormatAnnotationFormatterFactory;
import com.photowey.spring.in.action.trim.factory.TrimSpaceAnnotationFormatterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {@code FormatterConfigurer}
 *
 * @author photowey
 * @date 2023/06/17
 * @since 1.0.0
 */
@Configuration
public class FormatterConfigurer implements WebMvcConfigurer {

    //@Bean
    public ConfigurableWebBindingInitializer configurableWebBindingInitializer(FormattingConversionService mvcConversionService) {
        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();

        mvcConversionService.addFormatterForFieldAnnotation(this.formatterFactory());
        initializer.setConversionService(mvcConversionService);

        return initializer;

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(this.formatterFactory());
        registry.addFormatterForFieldAnnotation(this.trimSpaceTrimmerFactory());
    }

    public CnyFormatAnnotationFormatterFactory formatterFactory() {
        return new CnyFormatAnnotationFormatterFactory();
    }

    public TrimSpaceAnnotationFormatterFactory trimSpaceTrimmerFactory() {
        return new TrimSpaceAnnotationFormatterFactory();
    }
}
