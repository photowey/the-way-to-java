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
