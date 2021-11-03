package com.photowey.rocketmq.in.action.engine;

import com.photowey.rocketmq.in.action.property.RocketmqProperties;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * {@code RocketmqEngine}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class RocketmqEngine implements IRocketmqEngine {

    @Getter
    private ListableBeanFactory beanFactory;
    @Getter
    private ApplicationContext applicationContext;
    @Getter
    private Environment environment;

    // =========================================

    @Getter
    @Autowired
    private RocketmqProperties rocketmqProperties;

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
