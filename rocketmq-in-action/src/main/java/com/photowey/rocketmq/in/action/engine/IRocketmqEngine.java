package com.photowey.rocketmq.in.action.engine;

import com.photowey.rocketmq.in.action.property.RocketmqProperties;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * {@code IRocketmqEngine}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
public interface IRocketmqEngine extends IEngine, BeanFactoryAware, ApplicationContextAware, EnvironmentAware {

    ListableBeanFactory beanFactory();

    ApplicationContext applicationContext();

    Environment environment();

    // =========================================

    RocketmqProperties rocketmqProperties();
}
