package com.photowey.zookeeper.in.action.engine;

import com.photowey.zookeeper.in.action.property.ZookeeperProperties;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * {@code IZookeeperEngine}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
public interface IZookeeperEngine extends IEngine, BeanFactoryAware, ApplicationContextAware, EnvironmentAware {

    ListableBeanFactory beanFactory();

    ApplicationContext applicationContext();

    Environment environment();

    // =========================================

    ZookeeperProperties zookeeperProperties();

    ZooKeeper zooKeeper();

    ZkClient zkClient();

    CuratorFramework curatorFramework();
}
