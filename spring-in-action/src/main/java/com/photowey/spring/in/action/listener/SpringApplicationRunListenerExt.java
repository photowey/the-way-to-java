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
package com.photowey.spring.in.action.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@code SpringApplicationRunListenerExt}
 *
 * @author photowey
 * @date 2021/12/19
 * @since 1.0.0
 */
@Slf4j
public class SpringApplicationRunListenerExt implements SpringApplicationRunListener {

    private static AtomicBoolean stated = new AtomicBoolean(false);

    public SpringApplicationRunListenerExt(SpringApplication application, String[] args) {
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        if (stated.compareAndSet(false, true)) {
            System.out.println(("---- SpringApplicationRunListenerExt#handle the context-starting callback ----111"));
        }
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        log.info("---- SpringApplicationRunListenerExt#handle the environment-prepared callback ----222");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("---- SpringApplicationRunListenerExt#handle the context-prepared callback ----333");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("---- SpringApplicationRunListenerExt#handle the context-loaded callback ----444");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("---- SpringApplicationRunListenerExt#handle the started callback ----555");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("---- SpringApplicationRunListenerExt#handle the running callback ----666");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("---- SpringApplicationRunListenerExt#handle the failed callback ----777");
    }
}
