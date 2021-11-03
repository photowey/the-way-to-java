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
}
