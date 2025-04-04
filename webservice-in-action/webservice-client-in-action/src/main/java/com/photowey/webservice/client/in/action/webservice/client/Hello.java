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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "hello", targetNamespace = "http://server.webservice.service.action.in.webservice.photowey.com")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Hello {


    /**
     * 
     * @param payload
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "sayHello")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayHello", targetNamespace = "http://server.webservice.service.action.in.webservice.photowey.com", className = "com.photowey.webservice.client.in.action.webservice.client.SayHello")
    @ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://server.webservice.service.action.in.webservice.photowey.com", className = "com.photowey.webservice.client.in.action.webservice.client.SayHelloResponse")
    public String sayHello(
        @WebParam(name = "payload", targetNamespace = "")
        String payload);

    /**
     * 
     * @param payload
     * @return
     *     returns com.photowey.webservice.client.in.action.webservice.client.OpenapiResponse
     */
    @WebMethod(action = "sayModel")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayModel", targetNamespace = "http://server.webservice.service.action.in.webservice.photowey.com", className = "com.photowey.webservice.client.in.action.webservice.client.SayModel")
    @ResponseWrapper(localName = "sayModelResponse", targetNamespace = "http://server.webservice.service.action.in.webservice.photowey.com", className = "com.photowey.webservice.client.in.action.webservice.client.SayModelResponse")
    public OpenapiResponse sayModel(
        @WebParam(name = "payload", targetNamespace = "")
        HelloPayload payload);

}
