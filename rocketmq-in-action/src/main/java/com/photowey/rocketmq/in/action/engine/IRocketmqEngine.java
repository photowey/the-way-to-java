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
package com.photowey.rocketmq.in.action.engine;

import com.photowey.rocketmq.in.action.property.RocketmqProperties;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * {@code IRocketmqEngine}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
public interface IRocketmqEngine extends IEngine, BeanFactoryAware, ApplicationContextAware, EnvironmentAware {

    ListableBeanFactory beanFactory();

    ApplicationContext applicationContext();

    Environment environment();

    // =========================================

    RocketmqProperties rocketmqProperties();
}
