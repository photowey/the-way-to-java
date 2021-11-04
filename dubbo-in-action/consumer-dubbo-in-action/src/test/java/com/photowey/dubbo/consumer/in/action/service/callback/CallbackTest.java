package com.photowey.dubbo.consumer.in.action.service.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.photowey.dubbo.api.in.action.service.callback.CallbackService;
import com.photowey.dubbo.consumer.in.action.setup.AnnotationSetup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code CallbackTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)  // Junit5
@ContextConfiguration(classes = {AnnotationSetup.class})
public class CallbackTest {

    @Reference(check = false)
    private CallbackService callbackService;

    @Test
    void testCallback() {

        String name = callbackService.sayHello("haha");
        Assertions.assertEquals("Say hello, the name is:haha", name);

        this.callbackService.sayHelloCallback("haha", result -> log.info("receive the callback function invoke.the result is:{}", result));
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
}
