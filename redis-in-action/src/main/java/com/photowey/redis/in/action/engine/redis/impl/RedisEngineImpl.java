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
package com.photowey.redis.in.action.engine.redis.impl;

import com.photowey.redis.in.action.config.redis.RedisConfigure;
import com.photowey.redis.in.action.engine.redis.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.photowey.redis.in.action.constant.RedisBeanNameConstants.REDIS_CUSTOM_TEMPLATE_BEAN_NAME;

/**
 * {@code RedisEngineImpl}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class RedisEngineImpl implements IRedisEngine {

    @Getter
    private ListableBeanFactory beanFactory;
    @Getter
    private ApplicationContext applicationContext;
    @Getter
    private Environment environment;

    @Getter
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 通过 {@literal @}Qualifier("redisEngine") 注入自定义的 {@link RedisTemplate}
     *
     * @see {@link RedisConfigure#redisTemplate(org.springframework.data.redis.connection.RedisConnectionFactory)}
     */
    @Getter
    @Autowired
    @Qualifier(REDIS_CUSTOM_TEMPLATE_BEAN_NAME)
    private RedisTemplate<String, Object> redisTemplate;

    @Getter
    @Autowired
    private IStringEngine stringEngine;

    @Getter
    @Autowired
    private IHashEngine hashEngine;

    @Getter
    @Autowired
    private IListEngine listEngine;

    @Getter
    @Autowired
    private ISetEngine setEngine;

    @Getter
    @Autowired
    private IZSetEngine zsetEngine;

    @Getter
    @Autowired
    private IJedisEngine jedisEngine;

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
