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
package com.photowey.juc.in.action.disruptor.publisher;

import com.photowey.juc.in.action.disruptor.manager.DisruptorProducerManager;
import com.photowey.juc.in.action.disruptor.producer.Producer;
import com.photowey.juc.in.action.disruptor.subscriber.TaskSubscriber;
import com.photowey.juc.in.action.disruptor.task.factory.TaskFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * {@code DefaultDisruptorPublisher}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
public class DefaultDisruptorPublisher implements DisruptorPublisher, SmartInitializingSingleton {

    private final DisruptorProducerManager<String> producerManager;

    public DefaultDisruptorPublisher(DisruptorProducerManager<String> producerManager) {
        this.producerManager = producerManager;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.producerManager.start();
    }

    @Override
    public <T> void publish(final T data) {
        this.checkDefaultDataType(data);
        LocalDateTime now = LocalDateTime.now();
        long occur = this.toTimeStamp(now);
        Producer<String> producer = this.producerManager.getProducer();

        producer.onPublish(event -> {
            event.setData((String) data);
            event.setOccur(occur);
        });
    }

    @Override
    public <DT> void registerSubscriber(TaskSubscriber<DT> subscriber) {
        TaskFactory<String> taskFactory = this.producerManager.getTaskFactory();
        taskFactory.registerSubscriber((TaskSubscriber<String>) subscriber);
    }

    @Override
    public void close() {
        this.producerManager.getProducer().shutdown();
    }

    private <T> void checkDefaultDataType(T data) {
        if (!(data instanceof String)) {
            throw new IllegalArgumentException("Illegal default data type, must be java.lang.String.");
        }
    }

    public long toTimeStamp(LocalDateTime occur) {
        ZoneId zoneId = ZoneId.systemDefault();
        return occur.atZone(zoneId).toInstant().toEpochMilli();
    }
}
