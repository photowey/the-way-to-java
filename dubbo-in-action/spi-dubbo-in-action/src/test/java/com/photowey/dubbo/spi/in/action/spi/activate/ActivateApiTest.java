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
package com.photowey.dubbo.spi.in.action.spi.activate;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

/**
 * {@code ActivateApiTest}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
@Slf4j
class ActivateApiTest {

    @Test
    void testLoadDefault() {
        // Get by @SPI.value()
        ExtensionLoader<ActivateApi> extensionLoader = ExtensionLoader.getExtensionLoader(ActivateApi.class);
        ActivateApi defaultExtension = extensionLoader.getDefaultExtension();
        if (null != defaultExtension) {
            log.info("the default extension is:{}", defaultExtension.getClass());
        }
        String defaultExtensionName = extensionLoader.getDefaultExtensionName();
        log.info("the default extension name is:{}", defaultExtensionName);
    }

    @Test
    void testAdaptive() {
        // @Adaptive
        ActivateApi adaptiveExtension = ExtensionLoader.getExtensionLoader(ActivateApi.class).getAdaptiveExtension();
        log.info("the default adaptive extension is:{}", adaptiveExtension.getClass());
    }

    @Test
    void testLoadByName() {
        // Get by name
        ActivateApi mybatis = ExtensionLoader.getExtensionLoader(ActivateApi.class).getExtension("mybatis");
        log.info("the mybatis extension is:{}", mybatis.getClass());
        Set<String> supportedExtensions = ExtensionLoader.getExtensionLoader(ActivateApi.class).getSupportedExtensions();
        supportedExtensions.forEach(extension -> log.info("the candidate extension is:{}", extension));
    }

    @Test
    void testLoadSupportedExtensions() {
        // Get all
        Set<String> supportedExtensions = ExtensionLoader.getExtensionLoader(ActivateApi.class).getSupportedExtensions();
        supportedExtensions.forEach(extension -> log.info("the candidate extension is:{}", extension));

        // no spring.the SpringActivate + @Adaptive @Activate(group = {"spring"})
        // [main] INFO com.photowey.dubbo.spi.in.action.spi.activate.ActivateApiTest - the candidate extension is:dubbo
        // [main] INFO com.photowey.dubbo.spi.in.action.spi.activate.ActivateApiTest - the candidate extension is:kafka
        // [main] INFO com.photowey.dubbo.spi.in.action.spi.activate.ActivateApiTest - the candidate extension is:mybatis
        // main] INFO com.photowey.dubbo.spi.in.action.spi.activate.ActivateApiTest - the candidate extension is:rabbitmq
        // main] INFO com.photowey.dubbo.spi.in.action.spi.activate.ActivateApiTest - the candidate extension is:rocketmq
        // main] INFO com.photowey.dubbo.spi.in.action.spi.activate.ActivateApiTest - the candidate extension is:springboot

    }

    @Test
    void testGroup1() {
        URL url = URL.valueOf("test://localhost/test");
        List<ActivateApi> mqs = ExtensionLoader.getExtensionLoader(ActivateApi.class).getActivateExtension(url, new String[]{}, "mq");
        // the mq candidate extension size is:2
        log.info("the mq candidate extension size is:{}", mqs.size());
        mqs.forEach(mq -> log.info("the mq candidate extension is:{}", mq.getClass()));

        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.KafkaActivate
        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.RocketMQActivate
    }

    @Test
    void testGroup2() {
        URL url = URL.valueOf("test://localhost/test");
        url = url.addParameter("not-match-value", "mq");
        List<ActivateApi> mqs = ExtensionLoader.getExtensionLoader(ActivateApi.class).getActivateExtension(url, new String[]{}, "mq");
        // the mq candidate extension size is:2
        log.info("the mq candidate extension size is:{}", mqs.size());
        mqs.forEach(mq -> log.info("the mq candidate extension is:{}", mq.getClass()));

        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.KafkaActivate
        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.RocketMQActivate
    }

    @Test
    void testGroup3() {
        URL url = URL.valueOf("test://localhost/test");
        // match the parameter ket:value
        url = url.addParameter("value", "mq");
        List<ActivateApi> mqs = ExtensionLoader.getExtensionLoader(ActivateApi.class).getActivateExtension(url, new String[]{}, "mq");
        // the mq candidate extension size is:3
        log.info("the mq candidate extension size is:{}", mqs.size());
        mqs.forEach(mq -> log.info("the mq candidate extension is:{}", mq.getClass()));

        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.RabbitMQActivate
        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.KafkaActivate
        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.RocketMQActivate
    }

    @Test
    void testGroup4() {
        URL url = URL.valueOf("test://localhost/test");
        // match the parameter ket:value
        url = url.addParameter("value", "mq");
        List<ActivateApi> mqs = ExtensionLoader.getExtensionLoader(ActivateApi.class).getActivateExtension(url, new String[]{"springboot"}, "not-match");
        // the mq candidate extension size is:1
        log.info("the candidate extension size is:{}", mqs.size());
        mqs.forEach(mq -> log.info("the candidate extension is:{}", mq.getClass()));

        // the mq candidate extension is:class com.photowey.dubbo.spi.in.action.spi.activate.SpringBootActivate
    }

    @Test
    public void testProtocol() {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        log.info("the protocol extension is:{}", protocol.getClass());

        // the protocol extension is:class com.alibaba.dubbo.rpc.Protocol$Adaptive
        // dynamic
    }

    @Test
    public void testProxyFactory() {
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        log.info("the proxyFactory extension is:{}", proxyFactory.getClass());

        // the proxyFactory extension is:class com.alibaba.dubbo.rpc.ProxyFactory$Adaptive
        // dynamic

    }
}