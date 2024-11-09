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
package com.photowey.scheduled.in.action.notify;

/**
 * {@code DingtalkNotifier}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */

import com.photowey.common.in.action.util.ObjectUtils;
import com.photowey.scheduled.in.action.core.domain.payload.NotifyPayload;
import com.photowey.scheduled.in.action.notify.dingtalk.DingtalkMessageNotifier;
import com.photowey.scheduled.in.action.property.MessageProperties;
import com.photowey.scheduled.in.action.property.ScheduledProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

public interface DingtalkNotifier extends BeanFactoryAware, Ordered {

    String DEPLOY_TENANT_CLOUD = "cloud";

    String APP_KEY = "spring.application.name";
    String DOMAIN_KEY = "spring.application.domain";

    Logger logger();

    String template();

    BeanFactory beanFactory();

    default Environment environment() {
        return this.beanFactory().getBean(Environment.class);
    }

    default DingtalkMessageNotifier dingtalkMessageNotifier() {
        return this.beanFactory().getBean(DingtalkMessageNotifier.class);
    }

    default MessageProperties messageProperties() {
        return this.beanFactory().getBean(MessageProperties.class);
    }

    default ScheduledProperties scheduledProperties() {
        return this.beanFactory().getBean(ScheduledProperties.class);
    }

    default void dingtalk(String actonId, String actionName) {

    }

    default void dingtalk(String template, String title, String actonId, String actionName) {

    }

    default void dingtalk(String actonId, String actionName, String message) {

    }

    default void toDingtalk(NotifyPayload payload) {

    }

    default String app() {
        return this.environment().getProperty(APP_KEY);
    }

    default String domain() {
        MessageProperties messageProperties = this.beanFactory().getBean(MessageProperties.class);
        String domain = messageProperties.app().tryAcquireDomain();

        return ObjectUtils.defaultIfNull(domain, this.environment().getProperty(DOMAIN_KEY));
    }

    @Override
    default int getOrder() {
        return 0;
    }
}
