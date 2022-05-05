/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.dubbo.consumer.in.action.setup;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.photowey.dubbo.api.in.action.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code AnnotationSetupTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Slf4j
// @RunWith(SpringRunner.class) // Junit4 <- junit4 in class-path
@ExtendWith(SpringExtension.class)  // Junit5
@ContextConfiguration(classes = {AnnotationSetup.class})
class AnnotationSetupTest {

    @Reference(check = false)
    private UserService userService;

    @Test
    void testConsumerConsumeAnnotation() {
        String name = this.userService.sayHello("haha");
        log.info("handle invoke the dubbo rpc service,the result is:[{}]", name);
        Assertions.assertEquals("Say hello, the name is:haha", name);

        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    @Test
    void testConsumerConsumeApi() {

        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("consumer-dubbo-in-action");
        applicationConfig.setOwner("world");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://192.168.1.11:2182");

        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setConsumer(consumerConfig);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(UserService.class);
        // config -> dubbo.protocol.id=dubbo
        // config -> dubbo.protocol.name=dubbo
        // META-INFO/dubbo/internal/com.alibaba.dubbo.rpc.Protocol

        // java.lang.IllegalStateException: Invalid name="com.alibaba.dubbo.config.ProtocolConfig#0"
        // contains illegal character, only digit, letter, '-', '_' or '.' is legal.
        UserService userService = referenceConfig.get();

        String name = userService.sayHello("haha");
        log.info("handle invoke the dubbo rpc service,the result is:[{}]", name);
        Assertions.assertEquals("Say hello, the name is:haha", name);

        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    @Test
    void testEcho() {
        // EchoService
        // class com.sun.proxy.$Proxy41 cannot be cast to class com.alibaba.dubbo.rpc.service.EchoService
        // (com.sun.proxy.$Proxy41 and com.alibaba.dubbo.rpc.service.EchoService are in unnamed module of loader 'app')

        // EchoService
        // Use XML, it's BAD
        EchoService echoService = (EchoService) userService;
        String ok = (String) echoService.$echo("OK");
        Assertions.assertEquals("OK", ok);
    }
}