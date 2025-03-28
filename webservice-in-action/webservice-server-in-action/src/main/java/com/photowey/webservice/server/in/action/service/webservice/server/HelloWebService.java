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
package com.photowey.webservice.server.in.action.service.webservice.server;

import com.photowey.webservice.core.in.action.core.domain.payload.HelloPayload;
import com.photowey.webservice.core.in.action.core.response.OpenapiResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * {@code HelloWebService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/05
 */
@WebService(
    name = "hello",
    targetNamespace = "http://server.webservice.service.action.in.webservice.photowey.com"
)
public interface HelloWebService {

    @WebMethod(action = "sayHello")
    String sayHello(@WebParam(name = "payload") String payload);

    @WebMethod(action = "sayModel")
    OpenapiResponse sayModel(@WebParam(name = "payload") HelloPayload payload);
}
