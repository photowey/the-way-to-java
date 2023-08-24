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
package com.photowey.disruptor.in.action.config;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.photowey.disruptor.in.action.constant.DisruptorBrokerBeanNameConstants;
import com.photowey.disruptor.in.action.factory.NamedThreadFactory;
import com.photowey.disruptor.in.action.model.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ThreadFactory;

/**
 * {@code DisruptorBrokerBeansAutoConfigure}
 *
 * @author photowey
 * @date 2023/01/11
 * @since 1.0.0
 */
@AutoConfiguration(after = DisruptorBrokerBeansAutoConfigure.class)
public class DisruptorBrokerRingBufferAutoConfigure {

    public static final String RING_BUFFER_BEAN_NAME = DisruptorBrokerBeanNameConstants.RING_BUFFER_BEAN_NAME;
    public static final String EVENT_HANDLER_BEAN_NAME = DisruptorBrokerBeanNameConstants.EVENT_HANDLER_BEAN_NAME;
    public static final String EVENT_FACTORY_BEAN_NAME = DisruptorBrokerBeanNameConstants.EVENT_FACTORY_BEAN_NAME;
    public static final String WAIT_STRATEGY_BEAN_NAME = DisruptorBrokerBeanNameConstants.WAIT_STRATEGY_BEAN_NAME;

    public static final int BUFFER_SIZE = 1024 * 256;

    @Bean(RING_BUFFER_BEAN_NAME)
    @ConditionalOnMissingBean(name = RING_BUFFER_BEAN_NAME)
    public RingBuffer<Event> ringBuffer(
            @Qualifier(EVENT_FACTORY_BEAN_NAME) EventFactory<Event> factory,
            @Qualifier(WAIT_STRATEGY_BEAN_NAME) WaitStrategy waitStrategy,
            @Qualifier(EVENT_HANDLER_BEAN_NAME) EventHandler<Event> eventHandler) {

        ThreadFactory threadFactory = new NamedThreadFactory("disruptor", 1);
        Disruptor<Event> disruptor = new Disruptor<>(factory, BUFFER_SIZE, threadFactory, ProducerType.SINGLE, waitStrategy);
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();

        return disruptor.getRingBuffer();
    }
}
