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
