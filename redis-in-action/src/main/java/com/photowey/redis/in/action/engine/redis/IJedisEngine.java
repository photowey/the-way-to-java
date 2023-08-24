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
package com.photowey.redis.in.action.engine.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * {@code IJedisEngine}
 *
 * @author photowey
 * @date 2021/10/28
 * @since 1.0.0
 */
public interface IJedisEngine extends IEngine {

    /**
     * 获取 {@link JedisPool}
     *
     * @return {@link JedisPool}
     */
    JedisPool jedisPool();

    /**
     * 获取 {@link JedisSentinelPool}
     *
     * @return {@link JedisSentinelPool}
     */
    JedisSentinelPool jedisSentinelPool();

    /**
     * 获取 {@link JedisCluster}
     *
     * @return {@link JedisCluster}
     */
    JedisCluster jedisCluster();

    /**
     * 获取 {@link Jedis}
     *
     * @return {@link Jedis}
     */
    Jedis jedis();

    /**
     * 获取 {@link Jedis}
     *
     * @param mode mode == 0 Common Jedis; mode == 1 Sentinel Jedis
     *             获取 {@link Jedis}
     */
    Jedis jedis(int mode);
}
