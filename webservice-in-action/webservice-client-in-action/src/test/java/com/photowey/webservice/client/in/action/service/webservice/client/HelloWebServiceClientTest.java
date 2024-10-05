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
package com.photowey.webservice.client.in.action.service.webservice.client;

import com.photowey.webservice.client.in.action.AbstractTest;
import com.photowey.webservice.core.in.action.core.domain.payload.HelloPayload;
import com.photowey.webservice.core.in.action.core.domain.payload.Hobby;
import com.photowey.webservice.core.in.action.core.response.OpenapiResponse;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * {@code HelloWebServiceClientTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
@SpringBootTest
class HelloWebServiceClientTest extends AbstractTest {

    private Client client;

    @BeforeEach
    void init() {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        this.client = dcf.createClient("http://localhost:7923/ws/hello?wsdl");
    }

    // JDK 11
    // Caused by: java.lang.ClassNotFoundException: com/sun/tools/internal/xjc/api/XJC
    @Test
    void testWebServiceClient() throws Exception {
        String payload = this.requestBody();

        this.testSingle(payload);
        //this.testStreaming(payload);
    }

    private void testSingle(String payload) throws Exception {
        String xml = this.call("sayHello", payload);
        OpenapiResponse response = this.mapperProxy.parseXMLObject(xml, OpenapiResponse.class);

        Assertions.assertTrue(response.predicateIsSuccess());

        TimeUnit.SECONDS.sleep(1);
    }

    private void testStreaming(String payload) throws Exception {
        for (int i = 0; i < 5; i++) {
            String xml = this.call("sayHello", payload);
            OpenapiResponse response = this.mapperProxy.parseXMLObject(xml, OpenapiResponse.class);

            Assertions.assertTrue(response.predicateIsSuccess());

            TimeUnit.SECONDS.sleep(1);
        }
    }

    private String call(String service, String payload) throws Exception {
        Object[] responses = client.invoke(service, payload);

        return (String) responses[0];
    }

    private String requestBody() {
        Hobby hobby = Hobby.builder()
            .id(9527L)
            .name("badminton")
            .build();

        HelloPayload payload = HelloPayload.builder()
            .id(10086L)
            .age(18)
            .name("photowey")
            .hobbies(Arrays.asList(hobby))
            .build();

        return this.mapperProxy.toXMLString(payload);
    }
}
