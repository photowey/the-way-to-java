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
package com.photowey.webservice.server.in.action.service.webservice.server.impl;

import com.photowey.webservice.core.in.action.core.domain.payload.HelloPayload;
import com.photowey.webservice.core.in.action.core.enums.ApplicationContextHolder;
import com.photowey.webservice.core.in.action.core.response.OpenapiResponse;
import com.photowey.webservice.core.in.action.proxy.objectmapper.ObjectMapperProxy;
import com.photowey.webservice.server.in.action.service.webservice.server.HelloWebService;
import lombok.extern.slf4j.Slf4j;

import javax.jws.WebService;

/**
 * {@code HelloWebServiceImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/05
 */
@Slf4j
@WebService(
    name = "hello",
    targetNamespace = "http://server.webservice.service.action.in.webservice.photowey.com",
    endpointInterface = "com.photowey.webservice.server.in.action.service.webservice.server.HelloWebService"
)
public class HelloWebServiceImpl implements HelloWebService {

    @Override
    public String sayHello(String payload) {
        HelloPayload helloPayload = this.toPayload(payload);

        this.handleRequest(helloPayload);

        OpenapiResponse response = OpenapiResponse.ok()
            .body("Nice~")
            .build();

        return this.toXML(response);
    }

    @Override
    public OpenapiResponse sayModel(HelloPayload payload) {
        OpenapiResponse response = OpenapiResponse.ok()
            .body("Nice~")
            .build();

        return response;
    }

    // ----------------------------------------------------------------

    private void handleRequest(HelloPayload payload) {
        String json = this.toJSONString(payload);

        log.info("the webservice request json body is:\n{}", json);
    }

    // ----------------------------------------------------------------

    private HelloPayload toPayload(String payload) {
        ObjectMapperProxy proxy = ApplicationContextHolder.INSTANCE.getBean(ObjectMapperProxy.class);
        return proxy.parseXMLObject(payload, HelloPayload.class);
    }

    private <R> String toXML(R response) {
        ObjectMapperProxy proxy = ApplicationContextHolder.INSTANCE.getBean(ObjectMapperProxy.class);
        return proxy.toXMLString(response);
    }

    private <T> String toJSONString(T body) {
        ObjectMapperProxy proxy = ApplicationContextHolder.INSTANCE.getBean(ObjectMapperProxy.class);
        return proxy.toJSONString(body);
    }
}
