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
package com.photowey.spring.boot.v3.aot.client;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code HelloApiTest}
 *
 * @author photowey
 * @date 2022/12/13
 * @since 1.0.0
 */
//@SpringBootTest(classes = App.class)
class HelloApiTest {

    @Autowired
    private HelloApi helloApi;
    //@Autowired
    private HelloFeignApi helloFeignApi;

    //@Test
    void testHelloApi() throws InterruptedException {
        Map<String, Object> response = this.helloApi.get();
        Assertions.assertNotNull(response);

        TimeUnit.SECONDS.sleep(3_000);
    }

    //@Test
    void testHelloFeignApi() throws InterruptedException {
        // FIXME: org.springframework.beans.factory.ObjectProvider.stream(ObjectProvider.java:160)
        Map<String, Object> response = this.helloFeignApi.post();
        Assertions.assertNotNull(response);

        TimeUnit.SECONDS.sleep(3_000);
    }
}