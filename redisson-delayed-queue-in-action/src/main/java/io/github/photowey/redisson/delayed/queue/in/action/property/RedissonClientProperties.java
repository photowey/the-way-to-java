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
package io.github.photowey.redisson.delayed.queue.in.action.property;

import io.github.photowey.redisson.delayed.queue.in.action.core.enums.RedisModeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code RedissonClientProperties}
 *
 * @author photowey
 * @date 2024/03/02
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.client.redisson")
public class RedissonClientProperties {

    private boolean enabled = false;

    private RedisModeEnum mode = RedisModeEnum.SINGLE;
    private String address = "redis://127.0.0.1:6379";
    private String password = null;
    private String masterName = "master";
    private String namespace = "";
    private int database = 0;
    private int timeout = 10_000;
    private boolean ignoreLockingExceptions = false;

    private DelayedQueue delayed = new DelayedQueue();

    @Data
    public static class DelayedQueue implements Serializable {

        private String topic;
        private Set<String> topics = new HashSet<>();
        private long initialDelay = 0;
        private long period = 1;
        private TimeUnit unit = TimeUnit.SECONDS;
        private DelayedReport report = new DelayedReport();

        public Set<String> topics() {
            Set<String> topics = new HashSet<>(this.topics);
            if (!ObjectUtils.isEmpty(topic)) {
                topics.add(topic);
            }

            return topics;
        }
    }

    @Data
    public static class DelayedReport implements Serializable {

        private static final long serialVersionUID = -662198986634128016L;

        private String topicSet = "delayed:queue:redisson:report:topic:set";
        private String taskSet = "delayed:queue:redisson:report:task:set";
    }

    public static String getPrefix() {
        return "spring.redis.client.redisson";
    }
}