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
package com.photowey.juc.in.action.disruptor.manager;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.photowey.juc.in.action.disruptor.event.DataEvent;
import com.photowey.juc.in.action.disruptor.event.DisruptorEventFactory;
import com.photowey.juc.in.action.disruptor.handler.TaskHandler;
import com.photowey.juc.in.action.disruptor.producer.DisruptorProducer;
import com.photowey.juc.in.action.disruptor.task.factory.TaskFactory;
import com.photowey.juc.in.action.disruptor.thread.DisruptorThreadFactory;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code DisruptorProducerManager}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
public class DisruptorProducerManager<T> {

    public static final Integer DEFAULT_SIZE = 1 << 12;
    private static final Integer NCPU = Runtime.getRuntime().availableProcessors();
    private static final Integer DEFAULT_HANDLER_SIZE = NCPU;

    private final Integer ringBufferSize;
    private final Integer taskHandlerSize;

    @Getter
    private DisruptorProducer<T> producer;

    @Getter
    private TaskFactory<T> taskFactory;

    private ExecutorService taskExecutor;

    public DisruptorProducerManager(final TaskFactory<T> taskFactory) {
        this(taskFactory, DEFAULT_HANDLER_SIZE, DEFAULT_SIZE);
    }

    public DisruptorProducerManager(final TaskFactory<T> taskFactory, final Integer ringBufferSize) {
        this(taskFactory, DEFAULT_HANDLER_SIZE, ringBufferSize);
    }

    public DisruptorProducerManager(final TaskFactory<T> taskFactory, final int taskHandlerSize, final int ringBufferSize) {
        this.taskFactory = taskFactory;
        this.ringBufferSize = ringBufferSize;
        this.taskHandlerSize = taskHandlerSize;
        this.taskExecutor = new ThreadPoolExecutor(
                taskHandlerSize,
                taskHandlerSize << 1,
                1000L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                DisruptorThreadFactory.create("consumer", false),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public void start() {
        Disruptor<DataEvent<T>> disruptor = this.populateDisruptor();
        RingBuffer<DataEvent<T>> ringBuffer = disruptor.getRingBuffer();

        this.producer = new DisruptorProducer<>(ringBuffer, disruptor);
    }

    private Disruptor<DataEvent<T>> populateDisruptor() {
        Disruptor<DataEvent<T>> disruptor = new Disruptor<>(
                this.populateEventFactory(),
                ringBufferSize,
                DisruptorThreadFactory.create("producer-" + taskFactory.name(), false),
                ProducerType.MULTI,
                this.populateBlockingWaitStrategy()
        );
        TaskHandler<T>[] taskHandlers = new TaskHandler[taskHandlerSize];
        for (int i = 0; i < taskHandlerSize; i++) {
            taskHandlers[i] = new TaskHandler<>(taskExecutor, taskFactory);
        }

        disruptor.handleEventsWithWorkerPool(taskHandlers);
        disruptor.setDefaultExceptionHandler(this.populateExceptionHandler());
        disruptor.start();

        return disruptor;
    }

    private EventFactory<DataEvent<T>> populateEventFactory() {
        return new DisruptorEventFactory<>();
    }

    private WaitStrategy populateBlockingWaitStrategy() {
        return new BlockingWaitStrategy();
    }

    private ExceptionHandler populateExceptionHandler() {
        return new IgnoreExceptionHandler();
    }
}
