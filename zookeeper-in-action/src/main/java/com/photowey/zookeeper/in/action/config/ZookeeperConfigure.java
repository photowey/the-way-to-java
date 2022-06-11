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
package com.photowey.zookeeper.in.action.config;

import com.photowey.zookeeper.in.action.property.ZookeeperProperties;
import com.photowey.zookeeper.in.action.watcher.WatcherImpl;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

import java.io.IOException;

/**
 * {@code ZookeeperConfigure}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {ZookeeperProperties.class})
public class ZookeeperConfigure {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    @Bean(destroyMethod = "close")
    public ZooKeeper zooKeeper() throws IOException {
        return new ZooKeeper(
                this.zookeeperProperties.getConnectString(),
                this.zookeeperProperties.getSessionTimeout(),
                this.watcher()
        );
    }

    @Bean(destroyMethod = "close")
    public ZkClient zkClient() {
        return new ZkClient(
                this.zookeeperProperties.getConnectString(),
                this.zookeeperProperties.getSessionTimeout(),
                this.zookeeperProperties.getConnectionTimeout()
        );
    }

    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString(this.zookeeperProperties.getConnectString())
                .sessionTimeoutMs(this.zookeeperProperties.getSessionTimeout())
                .connectionTimeoutMs(this.zookeeperProperties.getConnectionTimeout())
                .retryPolicy(this.exponentialBackoffRetry())
                .build();

        curatorFramework.start();

        return curatorFramework;
    }

    @Bean
    public Watcher watcher() {
        return new WatcherImpl();
    }

    @Bean
    public RetryPolicy exponentialBackoffRetry() {
        return new ExponentialBackoffRetry(this.zookeeperProperties.getBaseSleepTimeMs(), this.zookeeperProperties.getMaxRetries());
    }

    /* ******************************* Zookeeper Lock ************************* */

    @Bean
    public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean() {
        return new CuratorFrameworkFactoryBean(this.zookeeperProperties.getConnectString());
    }

    @Bean
    public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
        return new ZookeeperLockRegistry(curatorFramework, "/zookeeper-lock");
    }
}
