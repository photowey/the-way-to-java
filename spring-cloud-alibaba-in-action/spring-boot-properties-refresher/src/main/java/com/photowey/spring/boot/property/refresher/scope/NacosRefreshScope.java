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
package com.photowey.spring.boot.property.refresher.scope;

import com.photowey.spring.boot.property.refresher.constant.NacosRefresherConstants;
import com.photowey.spring.boot.property.refresher.shared.spring.scope.GenericScope;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * {@code NacosRefreshScope}
 *
 * @author weichangjun
 * @date 2023/01/13
 * @since 1.0.0
 */
@ManagedResource
public class NacosRefreshScope extends GenericScope
        implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, Ordered {

    public static final String NACOS_REFRESH_SCOPE = NacosRefresherConstants.NACOS_REFRESH_SCOPE;

    private ApplicationContext context;

    private BeanDefinitionRegistry registry;

    private boolean eager = true;

    private int order = Ordered.LOWEST_PRECEDENCE - 100;

    /**
     * Creates a scope instance and gives it the default name: "nacosRefresh".
     */
    public NacosRefreshScope() {
        super.setName(NACOS_REFRESH_SCOPE);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Flag to determine whether all beans in refresh scope should be instantiated eagerly
     * on startup. Default true.
     *
     * @param eager The flag to set.
     */
    public void setEager(boolean eager) {
        this.eager = eager;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
        super.postProcessBeanDefinitionRegistry(registry);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.start(event);
    }

    public void start(ContextRefreshedEvent event) {
        if (event.getApplicationContext() == this.context && this.eager && this.registry != null) {
            this.eagerlyInitialize();
        }
    }

    private void eagerlyInitialize() {
        for (String name : this.context.getBeanDefinitionNames()) {
            BeanDefinition definition = this.registry.getBeanDefinition(name);
            if (this.getName().equals(definition.getScope()) && !definition.isLazyInit()) {
                Object bean = this.context.getBean(name);
                if (bean != null) {
                    bean.getClass();
                }
            }
        }
    }

    @ManagedOperation(description = "Dispose of the current instance of bean name " + "provided and force a refresh on next method execution.")
    public boolean refresh(String name) {
        if (!ScopedProxyUtils.isScopedTarget(name)) {
            // User wants to refresh the bean with this name but that isn't the one in the
            // cache...
            name = ScopedProxyUtils.getTargetBeanName(name);
        }
        // Ensure lifecycle is finished if bean was disposable
        if (super.destroy(name)) {
            //this.context.publishEvent(new RefreshScopeRefreshedEvent(name));
            return true;
        }
        return false;
    }

    public void refreshAll() {
        super.destroy();
        //this.context.publishEvent(new RefreshScopeRefreshedEvent());
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

}
