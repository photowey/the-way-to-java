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
package com.photowey.redis.in.action.engine.redis.impl;

import com.photowey.redis.in.action.engine.redis.IJedisEngine;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Objects;

/**
 * {@code JedisEngine}
 *
 * @author photowey
 * @date 2021/10/28
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class JedisEngine implements IJedisEngine {

    @Getter
    @Autowired
    private JedisPool jedisPool;

    @Getter
    @Autowired(required = false)
    private JedisSentinelPool jedisSentinelPool;

    @Getter
    @Autowired(required = false)
    private JedisCluster jedisCluster;

    /**
     * 统一获取 {@link Jedis}
     *
     * @return {@link Jedis}
     */
    @Override
    public Jedis jedis() {
        return this.jedis(0);
    }

    @Override
    public Jedis jedis(int mode) {
        if (0 == mode) {
            return this.commonJedis();
        }

        return this.sentinelJedis();
    }

    private Jedis commonJedis() {
        return this.jedisPool.getResource();
    }

    private Jedis sentinelJedis() {
        // TODO 如果启用了 {@link JedisSentinelPool}
        return Objects.requireNonNull(this.jedisSentinelPool).getResource();
    }

    private JedisCluster clusterJedis() {
        // TODO 如果启用了 {@link Redis Cluster}
        return Objects.requireNonNull(this.jedisCluster);
    }
}
