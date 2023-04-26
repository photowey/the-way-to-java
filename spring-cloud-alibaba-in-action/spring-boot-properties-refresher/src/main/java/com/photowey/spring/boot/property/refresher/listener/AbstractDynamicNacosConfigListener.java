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
package com.photowey.spring.boot.property.refresher.listener;

import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import com.alibaba.nacos.spring.factory.NacosServiceFactory;
import com.alibaba.nacos.spring.util.NacosBeanUtils;
import com.photowey.spring.boot.property.refresher.format.StringFormatter;
import com.photowey.spring.boot.property.refresher.shared.spring.scope.NacosContextRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;

import java.util.Collection;

/**
 * {@code AbstractDynamicNacosConfigListener}
 *
 * @author photowey
 * @date 2023/04/26
 * @since 1.0.0
 */
public abstract class AbstractDynamicNacosConfigListener extends AbstractConfigChangeListener implements InitializingBean, BeanFactoryAware {

    protected static final Logger log = LoggerFactory.getLogger(AbstractDynamicNacosConfigListener.class);

    protected static final String DEFAULT_GROUP = "DEFAULT_GROUP";
    protected static final String APP_KEY = "spring.application.name";

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NacosServiceFactory factory = NacosBeanUtils.getNacosServiceFactoryBean();
        Collection<ConfigService> configServices = factory.getConfigServices();

        this.registerListener(configServices);
    }

    @Override
    public void receiveConfigChange(ConfigChangeEvent event) {
        log.info("Dynamic nacos.refresh.listener receive config changed event:{}", event);
        NacosContextRefresher refresher = this.beanFactory.getBean(NacosContextRefresher.class);

        this.preRefresh(event);
        refresher.refresh();
        this.posRefresh(event);
    }

    public void preRefresh(ConfigChangeEvent event) {
        // do nothing
    }

    public void posRefresh(ConfigChangeEvent event) {
        // do nothing
    }

    /**
     * 注册监听器
     *
     * @param configServices {@link ConfigService}
     */
    public abstract void registerListener(Collection<ConfigService> configServices);

    public void addTemplateListener(ConfigService configService, String template) {
        this.addTemplateListener(configService, DEFAULT_GROUP, template);
    }

    public void addTemplateListener(ConfigService configService, String group, String template) {
        Environment environment = this.beanFactory.getBean(Environment.class);
        String app = environment.getProperty(APP_KEY);
        String dataId = StringFormatter.format(template, app);

        this.addListener(configService, group, dataId);
    }

    public void addListener(ConfigService configService, String dataId) {
        this.addListener(configService, DEFAULT_GROUP, dataId);
    }

    public void addListener(ConfigService configService, String group, String dataId) {
        try {
            configService.addListener(dataId, group, this);
            log.info("Add dynamic nacos.refresh.listener for config dataId:[{}]", dataId);
        } catch (Exception e) {
            log.error("Add dynamic nacos.refresh.listener for config dataId:[{}] failed", dataId, e);
            throw new RuntimeException(StringFormatter.format("Add dataId:[{}]'s listener failed", dataId), e);
        }
    }
}