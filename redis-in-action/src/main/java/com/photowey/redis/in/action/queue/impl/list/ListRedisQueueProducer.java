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
package com.photowey.redis.in.action.queue.impl.list;

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * {@code ListRedisQueue}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class ListRedisQueueProducer implements IListRedisQueueProducer {

    private final IRedisEngine redisEngine;

    public ListRedisQueueProducer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public void produce(String queue, String message) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            if (log.isInfoEnabled()) {
                log.info("redis:list:mq produce message on queue:[{}],message is:[{}]", queue, message);
            }
            jedis.lpush(queue, message);
        }
    }
}
