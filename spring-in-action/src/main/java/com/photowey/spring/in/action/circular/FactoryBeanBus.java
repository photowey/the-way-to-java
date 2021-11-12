package com.photowey.spring.in.action.circular;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * {@code FactoryBeanBus}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Component
public class FactoryBeanBus implements BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("on application event:{}", ContextRefreshedEvent.class.getName());

        log.info("-->FactoryBeanA:name{}", this.beanFactory.getBean(FactoryBeanA.class).getName());
        log.info("-->FactoryBeanB:name{}", this.beanFactory.getBean(FactoryBeanB.class).getName());
        log.info("-->FactoryBeanC:name{}", this.beanFactory.getBean(FactoryBeanC.class).getName());

        // trigger NullPointerException
        // log.info("-->FactoryBeanA:name{}", this.beanFactory.getBean(FactoryBeanA.class).getFactoryBeanB().getName());
        // log.info("-->FactoryBeanB:name{}", this.beanFactory.getBean(FactoryBeanB.class).getFactoryBeanA().getName());
        // log.info("-->FactoryBeanC:name{}", this.beanFactory.getBean(FactoryBeanC.class).getCircularBeanD().getName());

    }
}
