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
package com.photowey.webservice.client.in.action.service.webservice.client.service;

import com.photowey.webservice.client.in.action.AbstractTest;
import com.photowey.webservice.client.in.action.App;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

/**
 * {@code HelloWebServiceImplServiceTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/28
 */
@Slf4j
@SpringBootTest(classes = {
    App.class,
    HelloWebServiceImplServiceTest.ServiceConfigure.class,
})
class HelloWebServiceImplServiceTest extends AbstractTest {

    @Configuration
    public static class ServiceConfigure {}

    @Test
    void testStaticStub() {
        String payload = this.requestBody();

        /*HelloWebServiceImplService helloWebService = new HelloWebServiceImplService();
        String response = helloWebService.getHelloPort().sayHello(payload);

        log.info("stub: the Hello webservice response is:[{}]", response);*/
    }
}
