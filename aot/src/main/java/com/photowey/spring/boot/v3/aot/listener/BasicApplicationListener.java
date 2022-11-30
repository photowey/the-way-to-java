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
package com.photowey.spring.boot.v3.aot.listener;

import com.photowey.spring.boot.v3.aot.core.domain.entity.Customer;
import com.photowey.spring.boot.v3.aot.repository.CustomerRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * {@code BasicApplicationListener}
 *
 * @author photowey
 * @date 2022/11/27
 * @since 1.0.0
 */
@Component
public class BasicApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final CustomerRepository customerRepository;

    public BasicApplicationListener(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.customerRepository.saveAll(
                Stream.of("A", "B", "C")
                        .map(name -> new Customer(null, name))
                        .toList()
        ).forEach(System.out::println);

    }
}
