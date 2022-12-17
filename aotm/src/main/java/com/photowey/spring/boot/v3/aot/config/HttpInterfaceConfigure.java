/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.spring.boot.v3.aot.config;

import com.photowey.spring.boot.v3.aot.client.HelloApi;
import com.photowey.spring.boot.v3.aot.client.HelloFeignApi;
import feign.Feign;
import okhttp3.OkHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * {@code HttpInterfaceConfigure}
 *
 * @author photowey
 * @date 2022/12/13
 * @since 1.0.0
 */
@Configuration
public class HttpInterfaceConfigure {

    @Bean
    public HelloApi helloApi() {
        WebClient client = WebClient.builder().baseUrl("https://httpbin.org").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(HelloApi.class);
    }

    //@Bean
    public HelloFeignApi helloFeignApi(OkHttpClient okHttpClient, ObjectFactory<HttpMessageConverters> messageConverters) {
        return Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters, new EmptyObjectProvider()))
                .client(new feign.okhttp.OkHttpClient(okHttpClient))
                .target(HelloFeignApi.class, "https://httpbin.org");

    }

    public static class EmptyObjectProvider implements ObjectProvider {

        @Override
        public Object getObject() throws BeansException {
            return null;
        }

        @Override
        public Object getObject(Object... args) throws BeansException {
            return null;
        }

        @Override
        public Object getIfAvailable() throws BeansException {
            return null;
        }

        @Override
        public Object getIfUnique() throws BeansException {
            return null;
        }
    }
}
