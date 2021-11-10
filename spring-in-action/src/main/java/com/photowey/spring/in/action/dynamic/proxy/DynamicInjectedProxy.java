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
package com.photowey.spring.in.action.dynamic.proxy;

import com.photowey.spring.in.action.dynamic.proxy.cglib.CglibProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * {@code DynamicInjectedProxy}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
@Component
public class DynamicInjectedProxy {

    private static final List<ProxyFactory> PROXY_FACTORIES = new ArrayList<>(4);

    @PostConstruct
    public void init() {
        ServiceLoader<ProxyFactory> proxyFactories = ServiceLoader.load(ProxyFactory.class);
        Iterator<ProxyFactory> iterator = proxyFactories.iterator();
        while (iterator.hasNext()) {
            ProxyFactory proxyFactory = iterator.next();
            PROXY_FACTORIES.add(proxyFactory);
        }
    }

    public Object buildProxy(String type, AnnotationAttributes annotationAttributes, Class<?> targetType, ApplicationContext applicationContext) {
        for (ProxyFactory proxyFactory : PROXY_FACTORIES) {
            if (proxyFactory.supports(type)) {
                return proxyFactory.buildProxy(annotationAttributes, targetType, applicationContext);
            }
        }

        // 默认 Cglib
        CglibProxyFactory cglibProxyFactory = this.createCglibProxyFactory();

        return cglibProxyFactory.buildProxy(annotationAttributes, targetType, applicationContext);
    }

    private CglibProxyFactory createCglibProxyFactory() {
        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory();
        return cglibProxyFactory;
    }


}
