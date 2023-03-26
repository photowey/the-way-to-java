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
package com.photowey.disruptor.in.action.constant;

/**
 * {@code DisruptorBrokerBeanNameConstants}
 *
 * @author photowey
 * @date 2023/01/11
 * @since 1.0.0
 */
public interface DisruptorBrokerBeanNameConstants {

    String RING_BUFFER_BEAN_NAME = "com.lmax.disruptor.RingBuffer";
    String DISRUPTOR_BROKER_BEAN_NAME = "com.photowey.disruptor.in.action.broker.DisruptorBroker";
    String DISRUPTOR_MQ_SERVICE_BEAN_NAME = "com.photowey.disruptor.in.action.service.DisruptorMQService";

    @Deprecated
    String EXECUTOR_SERVICE_BEAN_NAME = "disruptor.executor.service";
    String EVENT_HANDLER_BEAN_NAME = "com.lmax.disruptor.EventHandler";
    String EVENT_FACTORY_BEAN_NAME = "com.lmax.disruptor.EventFactory";
    String WAIT_STRATEGY_BEAN_NAME = "com.lmax.disruptor.WaitStrategy";

}
