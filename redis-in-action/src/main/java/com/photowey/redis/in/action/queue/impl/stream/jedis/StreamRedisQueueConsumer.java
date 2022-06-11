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
package com.photowey.redis.in.action.queue.impl.stream.jedis;

import com.alibaba.fastjson.JSON;
import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import com.photowey.redis.in.action.queue.event.stream.StreamEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.StreamGroupInfo;
import redis.clients.jedis.params.XReadGroupParams;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@code StreamRedisQueueConsumer}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class StreamRedisQueueConsumer implements IStreamRedisQueueConsumer {

    private final IRedisEngine redisEngine;
    private ApplicationContext applicationContext;
    private ScheduledExecutorService scheduledExecutorService;

    public StreamRedisQueueConsumer(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void consume(String queue, String customer, String group) {
        if (!this.checkGroup(queue, group)) {
            this.createGroup(queue, group, null);
        }
        this.scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
                XReadGroupParams groupParams = new XReadGroupParams();
                groupParams.block(0).count(1);

                Map<String, StreamEntryID> streams = new HashMap<>();
                streams.put(queue, StreamEntryID.UNRECEIVED_ENTRY);
                List<Map.Entry<String, List<StreamEntry>>> context = jedis.xreadGroup(group, customer, groupParams, streams);
                if (this.isNotEmpty(context)) {
                    context.forEach((stream) -> {
                        List<StreamEntry> entries = stream.getValue();
                        entries.forEach((entry) -> {
                            Map<String, String> message = entry.getFields();
                            // TODO 需要 ACK 最好同步处理
                            if (log.isInfoEnabled()) {
                                log.info("redis:stream:mq consume message on queue:[{}],message is:{}", queue, JSON.toJSONString(message));
                            }
                            this.applicationContext.publishEvent(new StreamEvent(message));
                            this.ack(queue, group, entry.getID());
                            if (log.isInfoEnabled()) {
                                log.info("redis:stream:mq ack message on queue:[{}],message is:{} successfully...", queue, JSON.toJSONString(message));
                            }
                        });
                    });
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    // ================================================================================

    @Override
    public Long ack(String queue, String group, StreamEntryID id) {
        if (id == null) {
            throw new NullPointerException("id can't be null!");
        }
        return this.batchAck(queue, group, id);
    }

    @Override
    public Long batchAck(String queue, String group, StreamEntryID... ids) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            return jedis.xack(queue, group, ids);
        }
    }

    // ================================================================================

    @Override
    public void createGroup(String queue, String group, String offset) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            offset = ObjectUtils.isEmpty(offset) ? "0-0" : offset;
            StreamEntryID id = new StreamEntryID(offset);
            jedis.xgroupCreate(queue, group, id, false);
        }
    }

    @Override
    public boolean checkGroup(String queue, String group) {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            List<StreamGroupInfo> infoGroupResult = jedis.xinfoGroup(queue);
            for (StreamGroupInfo groupInfo : infoGroupResult) {
                if (group.equals(groupInfo.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }
}
