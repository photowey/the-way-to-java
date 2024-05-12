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
import org.redisson.config.*;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * {@code RedissonProperties}
 *
 * @author photowey
 * @date 2024/03/02
 * @since 1.0.0
 */
@Data
//@ConfigurationProperties(prefix = "spring.redis.client.redisson")
public class RedissonProperties {

    private static final long serialVersionUID = 6090214192615308329L;

    private boolean enabled = true;

    private RedisModeEnum mode = RedisModeEnum.SINGLE;
    private String address = "redis://127.0.0.1:6379";
    private String password = null;
    private String master = "master";
    private String namespace = "";
    private int database = 0;
    private int timeout = 10_000;

    // Unsupported now.
    //private ServerConfig server = new ServerConfig();

    private Delayed delayed = new Delayed();

    public static String getPrefix() {
        return "spring.redis.client.redisson";
    }

    public static class ServerConfig implements Serializable {

        private static final long serialVersionUID = 6168483442354177252L;

        private RedisModeEnum mode = RedisModeEnum.SINGLE;

        private SentinelServersConfig sentinel;

        private MasterSlaveServersConfig masterSlave;

        private SingleServerConfig single;

        private ClusterServersConfig cluster;

        private ReplicatedServersConfig replicated;

        // ----------------------------------------------------------------

        public void setMode(RedisModeEnum mode) {
            this.mode = mode;
        }

        public SentinelServersConfig getSentinel() {
            return sentinel;
        }

        public void setSentinel(SentinelServersConfig sentinel) {
            this.sentinel = sentinel;
        }

        public MasterSlaveServersConfig getMasterSlave() {
            return masterSlave;
        }

        public void setMasterSlave(MasterSlaveServersConfig masterSlave) {
            this.masterSlave = masterSlave;
        }

        public SingleServerConfig getSingle() {
            return single;
        }

        public void setSingle(SingleServerConfig single) {
            this.single = single;
        }

        public ClusterServersConfig getCluster() {
            return cluster;
        }

        public void setCluster(ClusterServersConfig cluster) {
            this.cluster = cluster;
        }

        public ReplicatedServersConfig getReplicated() {
            return replicated;
        }

        public void setReplicated(ReplicatedServersConfig replicated) {
            this.replicated = replicated;
        }

        // ----------------------------------------------------------------

        public RedisModeEnum getMode() {
            return mode;
        }

        public SentinelServersConfig sentinel() {
            return sentinel;
        }

        public MasterSlaveServersConfig masterSlave() {
            return masterSlave;
        }

        public SingleServerConfig single() {
            return single;
        }

        public ClusterServersConfig cluster() {
            return cluster;
        }

        public ReplicatedServersConfig replicated() {
            return replicated;
        }
    }

    public static class Delayed implements Serializable {

        private static final long serialVersionUID = -1873490325521362928L;

        private long max = Integer.MAX_VALUE;

        /**
         * Default topic.
         */
        private String topic;
        /**
         * Support topics.
         */
        private Set<String> topics = new HashSet<>();

        private Ticker ticker = new Ticker();
        private Poll poll = new Poll();
        private Registry registry = new Registry();

        // ----------------------------------------------------------------

        public Set<String> topics() {
            Set<String> topics = new HashSet<>(this.topics);
            if (!ObjectUtils.isEmpty(topics)) {
                topics.add(topic);
            }

            return topics;
        }

        // ----------------------------------------------------------------

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Set<String> getTopics() {
            return topics;
        }

        public void setTopics(Set<String> topics) {
            this.topics = topics;
        }

        public Ticker getTick() {
            return ticker;
        }

        public void setTick(Ticker ticker) {
            this.ticker = ticker;
        }

        public Poll getPoll() {
            return poll;
        }

        public void setPoll(Poll poll) {
            this.poll = poll;
        }

        public Registry getRegistry() {
            return registry;
        }

        public void setReport(Registry registry) {
            this.registry = registry;
        }

        // ----------------------------------------------------------------

        public long max() {
            return max;
        }

        public String topic() {
            return topic;
        }

        public Ticker ticker() {
            return ticker;
        }

        public Poll poll() {
            return poll;
        }

        public Registry registry() {
            return registry;
        }
    }

    public static class Ticker implements Serializable {

        private static final long serialVersionUID = 1921384691522476323L;

        private long initialDelay = 0;
        private long period = 1;
        private TimeUnit unit = TimeUnit.SECONDS;

        // ----------------------------------------------------------------

        public long getInitialDelay() {
            return initialDelay;
        }

        public void setInitialDelay(long initialDelay) {
            this.initialDelay = initialDelay;
        }

        public long getPeriod() {
            return period;
        }

        public void setPeriod(long period) {
            this.period = period;
        }

        public TimeUnit getUnit() {
            return unit;
        }

        public void setUnit(TimeUnit unit) {
            this.unit = unit;
        }

        // ----------------------------------------------------------------

        public long initialDelay() {
            return initialDelay;
        }

        public long period() {
            return period;
        }

        public TimeUnit unit() {
            return unit;
        }
    }

    public static class Poll implements Serializable {

        private static final long serialVersionUID = 1921384691522476323L;

        private long timeout = 2;
        private TimeUnit unit = TimeUnit.SECONDS;

        // ----------------------------------------------------------------

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public TimeUnit getUnit() {
            return unit;
        }

        public void setUnit(TimeUnit unit) {
            this.unit = unit;
        }

        // ----------------------------------------------------------------

        public long timeout() {
            return timeout;
        }

        public TimeUnit unit() {
            return unit;
        }
    }

    public static class Registry implements Serializable {

        private static final long serialVersionUID = -662198986634128016L;

        private String topicSet = "delayed:queue:redisson:report:topic:set";
        private String taskSet = "delayed:queue:redisson:report:task:set";

        public String getTopicSet() {
            return topicSet;
        }

        public void setTopicSet(String topicSet) {
            this.topicSet = topicSet;
        }

        public String getTaskSet() {
            return taskSet;
        }

        public void setTaskSet(String taskSet) {
            this.taskSet = taskSet;
        }

        // ----------------------------------------------------------------

        public String topicSet() {
            return topicSet;
        }

        public String taskSet() {
            return taskSet;
        }
    }

    // ----------------------------------------------------------------

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RedisModeEnum getMode() {
        return mode;
    }

    public void setMode(RedisModeEnum mode) {
        this.mode = mode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Delayed getDelayed() {
        return delayed;
    }

    public void setDelayed(Delayed delayed) {
        this.delayed = delayed;
    }

    // ----------------------------------------------------------------

    public boolean enabled() {
        return enabled;
    }

    public RedisModeEnum mode() {
        return mode;
    }

    public String address() {
        return address;
    }

    public String password() {
        return password;
    }

    public String master() {
        return master;
    }

    public String namespace() {
        return namespace;
    }

    public int database() {
        return database;
    }

    public int timeout() {
        return timeout;
    }

    public Delayed delayed() {
        return delayed;
    }
}