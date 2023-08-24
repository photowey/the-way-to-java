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
package com.photowey.juc.in.action.disruptor.config;

import com.photowey.juc.in.action.disruptor.manager.DisruptorProducerManager;
import com.photowey.juc.in.action.disruptor.publisher.DefaultDisruptorPublisher;
import com.photowey.juc.in.action.disruptor.publisher.DisruptorPublisher;
import com.photowey.juc.in.action.disruptor.subscriber.DefaultTaskSubscriber;
import com.photowey.juc.in.action.disruptor.subscriber.TaskSubscriber;
import com.photowey.juc.in.action.disruptor.task.factory.DefaultTaskFactory;
import com.photowey.juc.in.action.disruptor.task.factory.TaskFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code DisruptorConfigure}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
@Configuration
public class DisruptorConfigure {

    /**
     * Can’t use {@code @ConditionalOnMissingBean}
     * 1.If there is a {@link String} type event that needs to be published, you can directly use the built-in {@link DefaultDisruptorPublisher}
     * 2.If there are both {@link String} type and other types of events to be published,
     * if {@code @ConditionalOnMissingBean} is used, then {@link DefaultDisruptorPublisher} will not be instantiated.
     * <p>
     * Register your own {@link TaskSubscriber}:
     * <pre>
     *     {@literal @}Component
     *     public class DefaultDisruptorPublisherBeanPostProcessor implements BeanPostProcessor {
     *         {@literal @}Override
     *         public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
     *             if (bean instanceof DefaultDisruptorPublisher) {
     *                 DefaultDisruptorPublisher defaultDisruptorPublisher = (DefaultDisruptorPublisher) bean;
     *                 defaultDisruptorPublisher.registerSubscriber(new TaskSubscriberExt());
     *             }
     *
     *             return bean;
     *         }
     *     }
     *     public class TaskSubscriberExt implements TaskSubscriber<String> {
     *         {@literal @}Override
     *         public void execute(Collection<String> dataList) {
     *             // do your biz.
     *         }
     *     }
     * </pre>
     *
     * @return {@link DisruptorPublisher}
     */
    @Bean
    // @ConditionalOnMissingBean
    public DisruptorPublisher defaultDisruptorPublisher() {
        DefaultTaskFactory taskFactory = this.defaultTaskFactory();
        DefaultTaskSubscriber taskSubscriber = this.defaultTaskSubscriber();
        taskFactory.registerSubscriber(taskSubscriber);
        DisruptorProducerManager<String> producerManager = this.defaultDisruptorProducerManager(taskFactory);

        return new DefaultDisruptorPublisher(producerManager);
    }

    @Bean
    @ConditionalOnMissingBean(DefaultTaskFactory.class)
    public DefaultTaskFactory defaultTaskFactory() {
        return new DefaultTaskFactory();
    }

    /**
     * TODO
     * Note:
     * If you need to use the built-in {@link DisruptorPublisher} that
     * handles the {@link String} event type, you must re-register a {@code Bean} with an instance of {@link DefaultTaskSubscriber}.
     * <pre>
     *     public class DefaultTaskSubscriberExt extends DefaultTaskSubscriber {
     *         {@literal @}Override
     *         public void execute(Collection<String> dataList) {
     *             // do your biz.
     *         }
     *     }
     * </pre>
     *
     * @return {@link DefaultTaskSubscriber}
     */
    @Bean
    @ConditionalOnMissingBean(DefaultTaskSubscriber.class)
    public DefaultTaskSubscriber defaultTaskSubscriber() {
        return new DefaultTaskSubscriber();
    }

    @Bean
    public DisruptorProducerManager<String> defaultDisruptorProducerManager(TaskFactory<String> taskFactory) {
        return new DisruptorProducerManager<>(taskFactory);
    }
}
