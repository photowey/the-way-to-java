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
package com.photowey.spring.in.action;

import com.photowey.print.in.action.printer.AppPrinter;
import com.photowey.spring.in.action.dynamic.annotation.EnableDynamicInjected;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@code SpringApp}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
@EnableDynamicInjected
//@Import(value = {
//        JungleBeanDefinitionRegistryPostProcessor.class,
//})
//@EnableJungleService(basePackages = {
//        "com.photowey.spring.in.action.hello.service"
//})
public class SpringApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringApp.class, args);
        AppPrinter.print(applicationContext);
    }
}
