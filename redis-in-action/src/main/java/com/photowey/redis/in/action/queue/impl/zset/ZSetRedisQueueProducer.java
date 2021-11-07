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
package com.photowey.redis.in.action.queue.impl.zset;

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
public class ZSetRedisQueueProducer implements IZSetRedisQueueProducer {

    private final IRedisEngine redisEngine;

    public ZSetRedisQueueProducer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
    }

    @Override
    public void produceDelayed(String queue, String message, long delayedMillis) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            if (log.isInfoEnabled()) {
                log.info("redis:zset:mq produce delayed message on queue:[{}], delayed:[{}], message is:{}", queue, delayedMillis, message);
            }
            jedis.zadd(queue, delayedMillis, message);
        }
    }
}
