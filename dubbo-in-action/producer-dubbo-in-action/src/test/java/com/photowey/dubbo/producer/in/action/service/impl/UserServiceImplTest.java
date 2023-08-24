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
package com.photowey.dubbo.producer.in.action.service.impl;

import com.photowey.dubbo.api.in.action.service.user.UserService;
import com.photowey.dubbo.producer.in.action.setup.AnnotationSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@code UserServiceImplTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
class UserServiceImplTest {

    /**
     * ```shell
     * $ ls /dubbo/com.photowey.dubbo.api.in.action.service.user.UserService/providers
     * ```
     * <pre>
     * dubbo://192.168.137.1:29527/com.photowey.dubbo.api.in.action.service.user.UserService?\
     * anyhost=true&application=producer-dubbo-in-action&\
     * bean.name=com.photowey.dubbo.api.in.action.service.user.UserService&\
     * default.timeout=5000&dubbo=2.0.2&generic=false&\
     * interface=com.photowey.dubbo.api.in.action.service.user.UserService&\
     * methods=sayHello&owner=world&pid=480&side=provider&timeout=2000&timestamp=1636012331673
     * </pre>
     */
    @Test
    void testProviderRegisterXml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationProvider.xml");
        applicationContext.start();
        UserService userService = applicationContext.getBean(UserService.class);
        String name = userService.sayHello("haha");
        Assertions.assertEquals("Say hello, the name is:haha", name);

        try {
            System.in.read();
            applicationContext.close();
        } catch (Exception e) {
        }
    }

    @Test
    void testProviderRegisterAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AnnotationSetup.class);
        applicationContext.start();

        UserService userService = applicationContext.getBean(UserService.class);
        String name = userService.sayHello("haha");
        Assertions.assertEquals("Say hello, the name is:haha", name);

        try {
            System.in.read();
            applicationContext.close();
        } catch (Exception e) {
        }
    }

}