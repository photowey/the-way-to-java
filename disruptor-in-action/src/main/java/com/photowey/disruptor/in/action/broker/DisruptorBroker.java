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
package com.photowey.disruptor.in.action.broker;

import com.lmax.disruptor.RingBuffer;
import com.photowey.disruptor.in.action.model.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * {@code DisruptorBroker}
 *
 * @author photowey
 * @date 2023/01/09
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class DisruptorBroker<T> {

    private RingBuffer<Event<T>> buffer;

    // TODO

    public Event<T> populateEvent(long sequence, T message) {
        Event<T> event = this.buffer().get(sequence);
        event.setMessage(message);

        return event;
    }
}
