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
package com.photowey.spring.in.action.aop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * {@code AopServiceTest}
 *
 * @author photowey
 * @date 2021/11/17
 * @since 1.0.0
 */
@SpringBootTest
class AopServiceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testAop() {
        AopService aopService = this.applicationContext.getBean(AopService.class);
        Assertions.assertNotNull(aopService);
        Assertions.assertTrue(AopUtils.isCglibProxy(aopService));
        Assertions.assertEquals("Say hello from:AopService", aopService.sayHello());

        // --- >>> com.photowey.spring.in.action.interceptor.GlobalAdvice.invoke <<< ---
        // --- >>> com.photowey.spring.in.action.aop.AspectAop#around::before <<< ---
        // --- >>> com.photowey.spring.in.action.aop.AspectAop#around::after <<< ---

    }

}