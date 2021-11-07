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
