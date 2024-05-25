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
package com.photowey.spring.in.action.ctx.holder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photowey.common.in.action.shared.json.jackson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * {@code ApplicationContextInjector}
 *
 * @author photowey
 * @date 2023/09/06
 * @since 1.0.0
 */
@Component
public class ApplicationContextInjector implements ApplicationContextAware, DisposableBean {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        this.inject();
    }

    @Override
    public void destroy() throws Exception {
        this.clean();
    }

    private void clean() {
        ApplicationContextHolder.cleanSharedObjects();
        JSON.clean();
    }

    private void inject() {
        ApplicationContextHolder.INSTANCE.applicationContext(this.applicationContext);
        this.injectSharedObjectMapper();
    }

    private void injectSharedObjectMapper() {
        ObjectMapper objectMapper = this.applicationContext.getBean(ObjectMapper.class);
        JSON.injectSharedObjectMapper(objectMapper);
    }
}
