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
package io.github.photowey.spring.modulith.in.action;

import io.github.photowey.spring.modulith.in.action.order.Order;
import io.github.photowey.spring.modulith.in.action.order.OrderManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * {@code App}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/06/27
 */
@EnableAsync
@SpringBootApplication
@ConfigurationPropertiesScan
public class App {

    public static void main(String... args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);
        fire(applicationContext);
    }

    private static void fire(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBean(OrderManagement.class).complete(new Order());
    }
}
