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

import com.lmax.disruptor.RingBuffer;
import com.photowey.disruptor.in.action.broker.DisruptorBroker;
import com.photowey.disruptor.in.action.constant.DisruptorBrokerBeanNameConstants;
import com.photowey.disruptor.in.action.model.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * {@code DisruptorBrokerAutoConfigure}
 *
 * @author photowey
 * @date 2023/01/11
 * @since 1.0.0
 */
@AutoConfiguration(after = {DisruptorBrokerRingBufferAutoConfigure.class})
public class DisruptorBrokerAutoConfigure {

    public static final String RING_BUFFER_BEAN_NAME = DisruptorBrokerBeanNameConstants.RING_BUFFER_BEAN_NAME;
    public static final String DISRUPTOR_BROKER_BEAN_NAME = DisruptorBrokerBeanNameConstants.DISRUPTOR_BROKER_BEAN_NAME;

    @Bean(DISRUPTOR_BROKER_BEAN_NAME)
    @ConditionalOnMissingBean(name = DISRUPTOR_BROKER_BEAN_NAME)
    public DisruptorBroker disruptorBroker(@Qualifier(RING_BUFFER_BEAN_NAME) RingBuffer<Event> ringBuffer) {
        return new DisruptorBroker(ringBuffer);
    }
}
