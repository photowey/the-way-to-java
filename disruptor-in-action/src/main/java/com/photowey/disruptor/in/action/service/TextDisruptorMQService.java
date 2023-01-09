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
package com.photowey.disruptor.in.action.service;

import com.photowey.disruptor.in.action.broker.DisruptorBroker;
import com.photowey.disruptor.in.action.model.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * {@code TextDisruptorMQService}
 *
 * @author photowey
 * @date 2023/01/09
 * @since 1.0.0
 */
@Slf4j
public class TextDisruptorMQService implements DisruptorMQService {

    private final DisruptorBroker<String> broker;

    public TextDisruptorMQService(DisruptorBroker<String> broker) {
        this.broker = broker;
    }

    @Override
    public boolean publish(String message) {
        return this.publish(message, (event) -> {
        });
    }

    @Override
    public boolean publish(String message, Consumer<Event<String>> resolve) {
        return this.publish(message, resolve, (e) -> {
        });
    }

    @Override
    public boolean throwingPublish(String message, Consumer<Throwable> reject) {
        return this.publish(message, (event) -> {
        }, reject);
    }

    @Override
    public boolean publish(String message, Consumer<Event<String>> resolve, Consumer<Throwable> reject) {
        long sequence = this.broker.buffer().next();
        try {
            Event<String> event = this.broker.populateEvent(sequence, message);
            resolve.accept(event);

            return true;
        } catch (Throwable e) {
            log.error("publish message to disruptor queue exception, message: [{}]", message, e);
            reject.accept(e);
        } finally {
            this.broker.buffer().publish(sequence);
        }

        return false;
    }
}
