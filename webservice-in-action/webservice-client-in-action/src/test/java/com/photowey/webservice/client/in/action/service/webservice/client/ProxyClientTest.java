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
import com.photowey.webservice.client.in.action.App;
import com.photowey.webservice.client.in.action.service.webservice.client.service.HelloWebService;
import com.photowey.webservice.client.in.action.webservice.client.HelloClient;
import com.photowey.webservice.client.in.action.webservice.client.HelloPayload;
import com.photowey.webservice.client.in.action.webservice.client.OpenapiResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

/**
 * {@code ProxyClientTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/24
 */
@Slf4j
@SpringBootTest(classes = {
    App.class,
    ProxyClientTest.ClientConfigure.class,
})
class ProxyClientTest extends AbstractTest {

    @Autowired
    private HelloWebService helloWebService;

    @Configuration
    static class ClientConfigure {

        @Bean
        public JaxWsPortProxyFactoryBean helloWebService() throws Exception {
            JaxWsPortProxyFactoryBean proxy = new JaxWsPortProxyFactoryBean();
            proxy.setServiceName("HelloWebServiceImplService");
            proxy.setPortName("helloPort");
            proxy.setNamespaceUri("http://server.webservice.service.action.in.webservice.photowey.com");

            //proxy.setWsdlDocumentUrl(new URL("http://localhost:7923/ws/hello?wsdl"));
            Resource resource = new ClassPathResource("wsdl/hello.wsdl");
            proxy.setWsdlDocumentUrl(resource.getURL());

            proxy.setServiceInterface(HelloWebService.class);

            return proxy;
        }

        @Bean
        public Jaxb2Marshaller marshaller() {
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
            marshaller.setCheckForXmlRootElement(false);
            // this package must match the package in the <generatePackage> specified in
            // pom.xml
            marshaller.setContextPath("com.photowey.webservice.client.in.action.webservice.client");
            return marshaller;
        }

        @Bean
        public HelloClient helloClient(Jaxb2Marshaller marshaller) {
            HelloClient client = new HelloClient();
            client.setDefaultUri("http://localhost:7923/ws");
            client.setMarshaller(marshaller);
            client.setUnmarshaller(marshaller);

            return client;
        }
    }

    //@BeforeEach
    void init() {}

    @Test
    void testProxy() throws Exception {
        String payload = this.requestBody();
        String response = this.helloWebService.sayHello(payload);

        log.info("proxy: the Hello webservice response is:[{}]", response);
    }

    // FIXME
    //@Test
    void testClient() {
        HelloPayload payload = this.requestPayload();
        OpenapiResponse response = this.helloClient.sayModel(payload);

        log.info("client: the Hello webservice response is:[{}]", this.mapperProxy.toXMLString(response));
    }
}
