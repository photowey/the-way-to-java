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
package com.photowey.disruptor.in.action.broker;

import com.lmax.disruptor.RingBuffer;
import com.photowey.disruptor.in.action.model.Event;

/**
 * {@code DisruptorBroker}
 *
 * @author photowey
 * @date 2023/01/09
 * @since 1.0.0
 */
public class DisruptorBroker {

    private final RingBuffer<Event> buffer;

    public DisruptorBroker(RingBuffer<Event> buffer) {
        this.buffer = buffer;
    }

    // TODO

    public Event populateDefaultEvent(long sequence, Object message) {
        Event event = this.buffer().get(sequence);
        // with default topic
        event.setMessage(message);

        return event;
    }

    public Event populateEvent(long sequence, Event event) {
        Event target = this.buffer().get(sequence);
        target.setTopic(event.getTopic());
        target.setMessage(event.getMessage());

        return target;
    }

    public long sequence() {
        return this.next();
    }

    public void publish(long sequence) {
        this.buffer().publish(sequence);
    }

    private long next() {
        return this.buffer().next();
    }

    private RingBuffer<Event> buffer() {
        return this.buffer;
    }
}
