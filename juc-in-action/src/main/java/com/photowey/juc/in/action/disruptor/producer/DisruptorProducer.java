/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.juc.in.action.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.photowey.juc.in.action.disruptor.event.DataEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * {@code DisruptorProducer}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
@Slf4j
public class DisruptorProducer<T> implements Producer<T> {

    private final RingBuffer<DataEvent<T>> ringBuffer;

    private final Disruptor<DataEvent<T>> disruptor;

    public DisruptorProducer(final RingBuffer<DataEvent<T>> ringBuffer, final Disruptor<DataEvent<T>> disruptor) {
        this.ringBuffer = ringBuffer;
        this.disruptor = disruptor;
    }

    @Override
    public void onPublish(final Consumer<DataEvent<T>> function) {
        long position = this.ringBuffer.next();
        try {
            DataEvent<T> dataEvent = this.ringBuffer.get(position);
            function.accept(dataEvent);
            this.ringBuffer.publish(position);
        } catch (Exception e) {
            log.error("publish the data-event exception", e);
        }
    }

    @Override
    public void shutdown() {
        if (null != this.disruptor) {
            this.disruptor.shutdown();
        }
    }
}
