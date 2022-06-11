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

import com.photowey.redis.in.action.queue.impl.stream.IStreamRedisQueue;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.StreamEntryID;

/**
 * {@code IStreamRedisQueue}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public interface IStreamRedisQueueConsumer extends IStreamRedisQueue, ApplicationContextAware {

    void consume(String queue, String customer, String group);

    Long ack(String queue, String group, StreamEntryID id);

    Long batchAck(String queue, String group, StreamEntryID... ids);

    void createGroup(String queue, String group, String offset);

    boolean checkGroup(String queue, String group);
}
