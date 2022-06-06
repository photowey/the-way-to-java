/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.redis.in.action.config.redis;

import com.photowey.redis.in.action.property.redis.JedisProperties;
import com.photowey.redis.in.action.property.redis.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static com.photowey.redis.in.action.constant.RedisBeanNameConstants.*;

/**
 * {@code RedisConfigure}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {RedisProperties.class, JedisProperties.class})
public class RedisConfigure {

    @Autowired
    private RedisProperties redisProperties;
    @Autowired
    private JedisProperties jedisProperties;

    @Bean
    @ConditionalOnMissingBean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(this.jedisProperties.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(this.jedisProperties.getMaxWait());
        jedisPoolConfig.setMaxTotal(this.jedisProperties.getMaxTotal());
        jedisPoolConfig.setMinIdle(this.jedisProperties.getMinIdle());

        return jedisPoolConfig;
    }

    @Bean("redisPool")
    @ConditionalOnMissingBean
//    @ConditionalOnExpression("#{null != environment['spring.redis.password'] && !''.equals(environment['spring.redis.password'])}")
    @ConditionalOnExpression("#{T(org.springframework.util.StringUtils).hasText(environment['spring.redis.password'])==true}")
    public JedisPool passwordRedisPool() {
        JedisPoolConfig jedisPoolConfig = this.jedisPoolConfig();

        return new JedisPool(jedisPoolConfig,
                this.redisProperties.getHost(), this.redisProperties.getPort(), this.redisProperties.getTimeout(), this.redisProperties.getPassword());
    }

    @Bean("redisPool")
    @ConditionalOnMissingBean
    @ConditionalOnExpression("#{T(org.springframework.util.StringUtils).hasText(environment['spring.redis.password'])==false}")
    public JedisPool redisPool() {
        JedisPoolConfig jedisPoolConfig = this.jedisPoolConfig();

        return new JedisPool(jedisPoolConfig, this.redisProperties.getHost(), this.redisProperties.getPort(), this.redisProperties.getTimeout());
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(this.redisProperties.getHost());
        redisStandaloneConfiguration.setPort(this.redisProperties.getPort());
        redisStandaloneConfiguration.setDatabase(this.redisProperties.getDatabase());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(this.redisProperties.getPassword()));

        return redisStandaloneConfiguration;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = this.redisStandaloneConfiguration();
        JedisPoolConfig poolConfig = this.jedisPoolConfig();

        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfigurationBuilder =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.poolConfig(poolConfig);
        JedisClientConfiguration jedisClientConfiguration = jedisClientConfigurationBuilder.build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);

        return stringRedisTemplate;
    }

    @Bean(REDIS_CUSTOM_TEMPLATE_BEAN_NAME)
    @ConditionalOnMissingBean(name = REDIS_CUSTOM_TEMPLATE_BEAN_NAME)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        RedisSerializer<String> redisKeySerializer = this.redisKeySerializer();
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setHashKeySerializer(redisKeySerializer);

        RedisSerializer<Object> redisValueSerializer = this.redisValueSerializer();
        redisTemplate.setValueSerializer(redisValueSerializer);
        redisTemplate.setHashValueSerializer(redisValueSerializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean(REDIS_KEY_SERIALIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = REDIS_KEY_SERIALIZER_BEAN_NAME)
    public RedisSerializer<String> redisKeySerializer() {

        return new StringRedisSerializer();
    }

    @Bean(REDIS_VALUE_SERIALIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = REDIS_VALUE_SERIALIZER_BEAN_NAME)
    public RedisSerializer<Object> redisValueSerializer() {
        // return new Jackson2JsonRedisSerializer<>(Object.class);

        return new GenericJackson2JsonRedisSerializer();
    }
}
