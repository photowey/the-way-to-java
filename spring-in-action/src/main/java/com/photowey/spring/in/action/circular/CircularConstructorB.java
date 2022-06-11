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
package com.photowey.spring.in.action.circular;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * {@code CircularConstructorB}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Data
@Component
public class CircularConstructorB implements Serializable, SmartInitializingSingleton {

    private static final long serialVersionUID = 8876517903025011546L;

    private String name = "I'm CircularConstructorB~";

    private final CircularConstructorA circularConstructorA;

    // The dependencies of some of the beans in the application context form a cycle:
    @Lazy // 能解决循环依赖
    public CircularConstructorB(CircularConstructorA circularConstructorA) {
        this.circularConstructorA = circularConstructorA;
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("afterSingletonsInstantiated-->sayHello:{}", this.circularConstructorA.getName());
    }
}
