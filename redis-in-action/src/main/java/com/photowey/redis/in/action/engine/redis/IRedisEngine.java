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
package com.photowey.redis.in.action.engine.redis;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * {@code IRedisEngine}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
public interface IRedisEngine extends BeanFactoryAware, ApplicationContextAware, EnvironmentAware, IEngine {

    // ================================================== Inner Bean

    ListableBeanFactory beanFactory();

    ApplicationContext applicationContext();

    Environment environment();

    // ================================================== Template

    /**
     * 获取 {@link StringRedisTemplate}
     *
     * @return {@link StringRedisTemplate}
     */
    StringRedisTemplate stringRedisTemplate();

    /**
     * 获取 {@link RedisTemplate<String, Object>}
     *
     * @return {@link RedisTemplate<String, Object>}
     */
    RedisTemplate<String, Object> redisTemplate();

    // ================================================== Base-Data-Type

    /**
     * 获取 {@link IStringEngine}
     *
     * @return {@link IStringEngine}
     */
    IStringEngine stringEngine();

    /**
     * 获取 {@link IHashEngine}
     *
     * @return {@link IHashEngine}
     */
    IHashEngine hashEngine();

    /**
     * 获取 {@link IListEngine}
     *
     * @return {@link IListEngine}
     */
    IListEngine listEngine();

    /**
     * 获取 {@link ISetEngine}
     *
     * @return {@link ISetEngine}
     */
    ISetEngine setEngine();

    /**
     * 获取 {@link IZSetEngine}
     *
     * @return {@link IZSetEngine}
     */
    IZSetEngine zsetEngine();

    // ================================================== Jedis

    /**
     * 获取 {@link IJedisEngine}
     *
     * @return {@link IJedisEngine}
     */
    IJedisEngine jedisEngine();
}
