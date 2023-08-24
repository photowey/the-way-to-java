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
package com.photowey.event.bus.in.action.subscribe;

import com.google.common.eventbus.Subscribe;
import com.photowey.event.bus.in.action.factory.NamedThreadFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code Subscriber}
 *
 * @author photowey
 * @date 2021/11/14
 * @since 1.0.0
 */
public interface Subscriber<T> {

    int CORE_HANDLER_THREAD = 1;
    int MAX_HANDLER_THREAD = 1;

    ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            CORE_HANDLER_THREAD,
            MAX_HANDLER_THREAD,
            Integer.MAX_VALUE,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("event-bus", MAX_HANDLER_THREAD)
    );

    default boolean asyncExecute() {
        return true;
    }

    default void beforeEvent() {

    }

    void handleEvent(T event);

    @Subscribe
    default void onEvent(T event) {
        if (this.asyncExecute()) {
            this.getExecutorService().submit(() -> {
                this.syncExecute(event);
            });
        } else {
            this.syncExecute(event);
        }
    }

    default void syncExecute(T event) {
        this.beforeEvent();
        this.handleEvent(event);
        this.afterEvent();
    }

    default void afterEvent() {

    }

    @PreDestroy
    default void onShutdown() {
        this.getExecutorService().shutdownNow();
    }

    default ExecutorService getExecutorService() {
        return EXECUTOR_SERVICE;
    }
}
