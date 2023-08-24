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
package com.photowey.disruptor.in.action.service;

import com.photowey.disruptor.in.action.model.Event;

import java.util.function.Consumer;

/**
 * {@code DisruptorMQService}
 *
 * @author photowey
 * @date 2023/01/09
 * @since 1.0.0
 */
public interface DisruptorMQService {

    /**
     * Publish message to {@code Disruptor} Queue
     *
     * @param message the message
     * @return {@code boolean}
     */
    boolean publish(String message);

    boolean publish(String message, Consumer<Event> resolve);

    boolean throwingPublish(String message, Consumer<Throwable> reject);

    boolean publish(String message, Consumer<Event> resolve, Consumer<Throwable> reject);

    boolean publish(Event event);

    boolean publish(Event event, Consumer<Event> resolve);

    boolean throwingPublish(Event event, Consumer<Throwable> reject);

    boolean publish(Event event, Consumer<Event> resolve, Consumer<Throwable> reject);
}
