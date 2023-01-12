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
package com.photowey.event.bus.in.action.event.bus;

import com.photowey.event.bus.in.action.event.Event;
import com.photowey.event.bus.in.action.factory.NamedThreadFactory;
import com.photowey.event.bus.in.action.subscribe.Subscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code GuavaEventBus}
 *
 * @author photowey
 * @date 2021/11/14
 * @since 1.0.0
 */
@Slf4j
public class GuavaEventBus implements EventBus {

    private static final int CORE_THREAD = 1;
    private static final int MAX_THREAD = 1;
    private static final int DEFAULT_TOTAL_SIZE = 1;
    private static final int DEFAULT_CAPACITY = 2048;

    private final com.google.common.eventbus.EventBus eventBus;

    public GuavaEventBus(String identifier) {
        this(identifier, false);
    }

    public GuavaEventBus(String identifier, boolean async) {
        this(identifier, DEFAULT_CAPACITY, async);
    }

    public GuavaEventBus(String identifier, int capacity, boolean async) {
        this(identifier, capacity, DEFAULT_TOTAL_SIZE, async);
    }

    public GuavaEventBus(String identifier, int capacity, int totalSize, boolean async) {
        this(identifier, async, new ThreadPoolExecutor(
                CORE_THREAD,
                MAX_THREAD,
                Integer.MAX_VALUE,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(capacity),
                new NamedThreadFactory(identifier, totalSize),
                (r, executor) -> log.warn("eventBus executor queue is full, size:{}", executor.getQueue().size())
        ));
    }

    public GuavaEventBus(String identifier, boolean async, ExecutorService eventExecutor) {
        if (async) {
            this.eventBus = new com.google.common.eventbus.AsyncEventBus(identifier, eventExecutor);
        } else {
            this.eventBus = new com.google.common.eventbus.EventBus(identifier);
        }
    }

    @Override
    public <T extends Subscriber> void register(T subscriber) {
        this.eventBus.register(subscriber);
    }

    @Override
    public <T extends Subscriber> void unregister(T subscriber) {
        this.eventBus.unregister(subscriber);
    }

    @Override
    public <T extends Event> void post(T event) {
        this.eventBus.post(event);
    }
}
