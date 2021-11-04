package com.photowey.dubbo.consumer.in.action.service.impl;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.photowey.dubbo.api.in.action.service.user.UserService;
import com.photowey.dubbo.consumer.in.action.service.ConsumerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@code ConsumerServiceImplTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
class ConsumerServiceImplTest {

    /**
     * ```shell
     * $ ls /dubbo/com.photowey.dubbo.api.in.action.service.user.UserService/consumers
     * ```
     * <pre>
     *     consumer://192.168.137.1/com.photowey.dubbo.api.in.action.service.user.UserService?\
     *     application=consumer-dubbo-in-action&category=consumers&check=false&default.timeout=5000&\
     *     dubbo=2.0.2&interface=com.photowey.dubbo.api.in.action.service.user.UserService&\
     *     methods=sayHello&owner=world&pid=13620&side=consumer&timeout=2000&timestamp=1636013701168
     * </pre>
     */
    @Test
    void testConsumerConsumeXml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationConsumer.xml");
        applicationContext.start();
        ConsumerService consumerService = applicationContext.getBean(ConsumerService.class);
        String name = consumerService.sayHello("haha");
        Assertions.assertEquals("Say hello, the name is:haha", name);

        try {
            System.in.read();
            applicationContext.close();
        } catch (Exception e) {
        }
    }

    @Test
    void testEcho() {
        // EchoService
        // Use XML, it's OK
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationConsumer.xml");
        UserService userService = applicationContext.getBean(UserService.class);
        EchoService echoService = (EchoService) userService;
        String ok = (String) echoService.$echo("OK");
        Assertions.assertEquals("OK", ok);
    }
}