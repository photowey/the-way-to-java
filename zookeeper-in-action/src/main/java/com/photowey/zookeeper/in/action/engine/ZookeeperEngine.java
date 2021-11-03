package com.photowey.zookeeper.in.action.engine;

import com.photowey.zookeeper.in.action.property.ZookeeperProperties;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * {@code ZookeeperEngine}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class ZookeeperEngine implements IZookeeperEngine {

    @Getter
    private ListableBeanFactory beanFactory;
    @Getter
    private ApplicationContext applicationContext;
    @Getter
    private Environment environment;

    // =========================================

    @Getter
    @Autowired
    private ZookeeperProperties zookeeperProperties;

    @Getter
    @Autowired
    private ZooKeeper zooKeeper;

    @Getter
    @Autowired
    private ZkClient zkClient;

    @Getter
    @Autowired
    private CuratorFramework curatorFramework;

    // =========================================

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
