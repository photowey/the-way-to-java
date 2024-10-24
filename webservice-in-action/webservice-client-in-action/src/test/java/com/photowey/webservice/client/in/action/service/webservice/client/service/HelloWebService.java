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

import com.photowey.webservice.client.in.action.webservice.client.SayModel;
import com.photowey.webservice.client.in.action.webservice.client.SayModelResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * {@code HelloWebService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/24
 */
@WebService
public interface HelloWebService {
    @WebMethod(action = "sayHello")
    String sayHello(@WebParam(name = "payload") String payload);

    @WebMethod(action = "sayModel")
    SayModelResponse sayModel(@WebParam(name = "payload") SayModel payload);
}

