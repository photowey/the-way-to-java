/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.spring.cloud.alibaba.seata.consumer.in.action.registry;

import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * {@code CustomNacosServiceRegistry}
 * The old Name {@code NacosServiceRegistry}conflicted with {@link NacosServiceRegistryAutoConfiguration#nacosServiceRegistry(com.alibaba.cloud.nacos.NacosDiscoveryProperties)}
 *
 * @author photowey
 * @date 2021/11/14
 * @since 1.0.0
 */
@Slf4j
// @Component
public class CustomNacosServiceRegistry implements SmartInitializingSingleton {

    @NacosInjected
    private NamingService namingService;

    @Value("${server.port}")
    private int serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void afterSingletonsInstantiated() {

        try {
            this.namingService.registerInstance(applicationName, "127.0.0.1", serverPort);
        } catch (NacosException e) {
            log.error("nacos registry service:{} exception", applicationName, e);
        }

    }
}
