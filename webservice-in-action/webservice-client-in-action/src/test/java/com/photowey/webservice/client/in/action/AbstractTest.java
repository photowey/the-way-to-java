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
package com.photowey.webservice.client.in.action;

import com.photowey.webservice.client.in.action.webservice.client.HelloClient;
import com.photowey.webservice.core.in.action.proxy.objectmapper.ObjectMapperProxy;
import org.apache.cxf.endpoint.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringWriter;
import java.util.Collections;
import java.util.function.BiFunction;

/**
 * {@code AbstractTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
public abstract class AbstractTest {

    @Autowired
    protected ObjectMapperProxy mapperProxy;

    @Autowired(required = false)
    @Qualifier("helloTemplate")
    protected WebServiceTemplate helloTemplate;

    @Autowired(required = false)
    @Qualifier("dynamicClient")
    protected Client dynamicClient;

    @Autowired(required = false)
    protected HelloClient helloClient;

    // ----------------------------------------------------------------

    protected String sendAndReceive(String payload) {
        return this.sendAndReceive(payload, (callback, extractor) -> {
            return this.helloTemplate.sendAndReceive(
                callback,
                extractor
            );
        });
    }

    protected String sendAndReceive(String uri, String payload) {
        return this.sendAndReceive(payload, (callback, extractor) -> {
            return this.helloTemplate.sendAndReceive(
                uri,
                callback,
                extractor
            );
        });
    }

    protected String sendAndReceive(String payload, BiFunction<WebServiceMessageCallback, WebServiceMessageExtractor<String>, String> fx) {
        WebServiceMessageCallback requestCallback = message -> {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result result = message.getPayloadResult();
            transformer.transform(new StringSource(payload), result);
        };

        WebServiceMessageExtractor<String> responseExtractor = message -> {
            StreamSource source = (StreamSource) message.getPayloadSource();
            StringWriter writer = new StringWriter();
            TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(writer));
            return writer.toString();
        };

        return fx.apply(requestCallback, responseExtractor);
    }

    // ----------------------------------------------------------------

    protected String requestBody() {
        return this.mapperProxy.toXMLString(this.requestPayload());
    }

    protected com.photowey.webservice.client.in.action.webservice.client.HelloPayload requestPayload() {
        com.photowey.webservice.client.in.action.webservice.client.Hobby hobby = com.photowey.webservice.client.in.action.webservice.client.Hobby.builder()
            .id(9527L)
            .name("badminton")
            .build();

        com.photowey.webservice.client.in.action.webservice.client.HelloPayload payload = com.photowey.webservice.client.in.action.webservice.client.HelloPayload.builder()
            .id(10086L)
            .age(18)
            .name("photowey")
            .list(Collections.singletonList(hobby))
            .build();

        return payload;
    }
}
