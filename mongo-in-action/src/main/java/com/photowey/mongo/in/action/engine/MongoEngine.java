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
package com.photowey.mongo.in.action.engine;

import com.photowey.mongo.in.action.generator.IKeyGenerator;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * {@code MongoEngine}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class MongoEngine implements IMongoEngine {

    @Getter
    private ListableBeanFactory beanFactory;
    @Getter
    private ApplicationContext applicationContext;
    @Getter
    private Environment environment;

    // =========================================

    @Getter
    @Autowired
    private IRepositoryEngine repositoryEngine;
    @Getter
    @Autowired
    private IServiceEngine serviceEngine;

    // =========================================

    @Getter
    @Autowired
    private MongoTemplate mongoTemplate;

    // =========================================

    @Getter
    @Autowired
    private IKeyGenerator keyGenerator;

    // =========================================

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
