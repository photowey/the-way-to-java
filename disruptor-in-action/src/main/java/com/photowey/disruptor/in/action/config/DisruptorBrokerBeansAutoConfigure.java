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
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.photowey.disruptor.in.action.broker.DisruptorBroker;
import com.photowey.disruptor.in.action.constant.DisruptorBrokerBeanNameConstants;
import com.photowey.disruptor.in.action.factory.DisruptorEventFactory;
import com.photowey.disruptor.in.action.handler.DisruptorEventHandler;
import com.photowey.disruptor.in.action.model.Event;
import com.photowey.disruptor.in.action.service.DisruptorMQService;
import com.photowey.disruptor.in.action.service.TextDisruptorMQService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * {@code DisruptorBrokerBeansAutoConfigure}
 *
 * @author photowey
 * @date 2023/01/11
 * @since 1.0.0
 */
@AutoConfiguration
public class DisruptorBrokerBeansAutoConfigure {

    public static final String DISRUPTOR_MQ_SERVICE_BEAN_NAME = DisruptorBrokerBeanNameConstants.DISRUPTOR_MQ_SERVICE_BEAN_NAME;
    public static final String EVENT_HANDLER_BEAN_NAME = DisruptorBrokerBeanNameConstants.EVENT_HANDLER_BEAN_NAME;
    public static final String EVENT_FACTORY_BEAN_NAME = DisruptorBrokerBeanNameConstants.EVENT_FACTORY_BEAN_NAME;
    public static final String WAIT_STRATEGY_BEAN_NAME = DisruptorBrokerBeanNameConstants.WAIT_STRATEGY_BEAN_NAME;

    @Bean(DISRUPTOR_MQ_SERVICE_BEAN_NAME)
    @ConditionalOnMissingBean(name = DISRUPTOR_MQ_SERVICE_BEAN_NAME)
    public DisruptorMQService disruptorMQService(DisruptorBroker disruptorBroker) {
        return new TextDisruptorMQService(disruptorBroker);
    }

    @Bean(EVENT_FACTORY_BEAN_NAME)
    @ConditionalOnMissingBean(name = EVENT_FACTORY_BEAN_NAME)
    public EventFactory<Event> eventFactory() {
        return new DisruptorEventFactory();
    }

    @Bean(WAIT_STRATEGY_BEAN_NAME)
    @ConditionalOnMissingBean(name = WAIT_STRATEGY_BEAN_NAME)
    public WaitStrategy waitStrategy() {
        // BlockingWaitStrategy();
        return new YieldingWaitStrategy();
    }

    @Bean(EVENT_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = EVENT_HANDLER_BEAN_NAME)
    public EventHandler<Event> eventHandler() {
        return new DisruptorEventHandler();
    }
}
