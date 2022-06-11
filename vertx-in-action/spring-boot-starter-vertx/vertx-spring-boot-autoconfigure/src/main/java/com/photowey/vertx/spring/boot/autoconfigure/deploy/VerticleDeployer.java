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
package com.photowey.vertx.spring.boot.autoconfigure.deploy;

import com.photowey.vertx.spring.boot.autoconfigure.property.VertxProperties;
import com.photowey.vertx.spring.boot.autoconfigure.server.HttpServerVerticle;
import com.photowey.vertx.spring.boot.core.model.HandlerMapping;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * {@code VerticleDeployer}
 *
 * @author photowey
 * @date 2022/02/17
 * @since 1.0.0
 */
@Slf4j
public class VerticleDeployer implements ApplicationContextAware, SmartInitializingSingleton {

    private ApplicationContext applicationContext;
    private volatile Throwable throwable;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.start();
    }

    private void start() {
        VertxProperties props = this.applicationContext.getBean(VertxProperties.class);

        List<HandlerMapping> mappings = props.getHandlerMappings();
        if (CollectionUtils.isEmpty(mappings)) {
            throw new IllegalStateException("handler mappings not found(404)");
        }

        this.printHandlerMappingsIfNecessary(applicationContext, props);

        this.deployVerticle(applicationContext, props);
    }

    private void printHandlerMappingsIfNecessary(ApplicationContext applicationContext, VertxProperties props) {
        List<HandlerMapping> mappings = props.getHandlerMappings();
        for (HandlerMapping hm : mappings) {
            List<String> handlers = new ArrayList<>(hm.getHandlers().size());
            for (String handler : hm.getHandlers()) {
                handlers.add(applicationContext.getBean(handler).getClass().getName());
            }

            if (props.getPrint().isEnabled()) {
                log.info("the handler mapping method-path:[{}-{}] to handler: [{}]", hm.getMethod(), hm.getPath(), String.join(",", handlers));
            }
        }
    }

    private void deployVerticle(ApplicationContext applicationContext, VertxProperties props) {
        VertxOptions vertxOptions = this.populateVertxOptions(props);

        Vertx vertx = Vertx.vertx(vertxOptions);

        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setInstances(props.getInstances());

        HttpServerVerticle.setApplicationContext(applicationContext);

        CountDownLatch latch = new CountDownLatch(1);
        vertx.deployVerticle(HttpServerVerticle.class, deploymentOptions, handler -> {
            if (handler.succeeded()) {
                log.info("vertx verticle deployed successfully on port(s): {} (http)", props.getPort());
                latch.countDown();
                return;
            }
            this.throwable = handler.cause();
            latch.countDown();
        });

        try {
            latch.await();
            if (null != throwable) {
                vertx.close();
                throw throwable;
            }
        } catch (Throwable e) {
            throw new RuntimeException("deploy the vertx verticle app failed to", e);
        }
    }

    private VertxOptions populateVertxOptions(VertxProperties props) {
        VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setEventLoopPoolSize(props.getEventLoopPoolSize())
                .setWorkerPoolSize(props.getWorkerPoolSize())
                .setMaxEventLoopExecuteTime(props.getMaxEventLoopExecuteTime())
                .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);

        return vertxOptions;
    }

}
