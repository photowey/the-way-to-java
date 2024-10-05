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
package com.photowey.webservice.server.in.action.config;

import com.photowey.webservice.core.in.action.annotation.EnableWebserviceCore;
import com.photowey.webservice.server.in.action.service.webservice.server.HelloWebService;
import com.photowey.webservice.server.in.action.service.webservice.server.impl.HelloWebServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * {@code HelloConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
@Configuration
@EnableWebserviceCore
public class HelloConfigure {

    @Autowired
    @Qualifier(Bus.DEFAULT_BUS_ID)
    private Bus bus;

    @Bean
    public HelloWebService helloWebService() {
        return new HelloWebServiceImpl();
    }

    @Bean
    public Endpoint endpoint(HelloWebService helloWebService) {
        EndpointImpl endpoint = new EndpointImpl(this.bus, helloWebService);
        endpoint.publish("/hello");

        return endpoint;
    }
}
