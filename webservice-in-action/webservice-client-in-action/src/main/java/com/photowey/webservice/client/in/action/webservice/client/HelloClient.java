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
package com.photowey.webservice.client.in.action.webservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * {@code HelloClient}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/24
 */
public class HelloClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(HelloClient.class);

    public OpenapiResponse sayModel(HelloPayload payload) {
        SayModel body = new SayModel();
        body.setPayload(payload);

        SayModelResponse response = (SayModelResponse) getWebServiceTemplate()
            .marshalSendAndReceive("http://localhost:7923/ws/hello", body,
                new SoapActionCallback("sayModel"));

        return response.getReturn();
    }
}
