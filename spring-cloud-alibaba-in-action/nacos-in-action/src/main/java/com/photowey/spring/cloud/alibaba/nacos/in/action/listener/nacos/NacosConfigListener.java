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
package com.photowey.spring.cloud.alibaba.nacos.in.action.listener.nacos;

import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import com.alibaba.nacos.spring.factory.NacosServiceFactory;
import com.alibaba.nacos.spring.util.NacosBeanUtils;
import com.photowey.spring.cloud.alibaba.nacos.in.action.scope.NacosContextRefresher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * {@code NacosConfigListener}
 *
 * @author weichangjun
 * @date 2023/01/13
 * @since 1.0.0
 */
@Slf4j
@Component("com.photowey.spring.cloud.alibaba.nacos.in.action.listener.nacos.NacosConfigListener")
public class NacosConfigListener extends AbstractConfigChangeListener implements InitializingBean, BeanFactoryAware {

    private static final String NACOS_IN_ACTION_DATA_ID = "nacos-in-action";

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NacosServiceFactory factory = NacosBeanUtils.getNacosServiceFactoryBean();
        Collection<ConfigService> configServices = factory.getConfigServices();
        for (ConfigService configService : configServices) {
            configService.addListener(NACOS_IN_ACTION_DATA_ID, "DEFAULT_GROUP", this);
        }
    }

    @Override
    public void receiveConfigChange(ConfigChangeEvent event) {
        NacosContextRefresher refresher = this.beanFactory.getBean(NacosContextRefresher.class);
        refresher.refresh();
    }
}