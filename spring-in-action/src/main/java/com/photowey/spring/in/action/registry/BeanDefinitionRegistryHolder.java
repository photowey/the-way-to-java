package com.photowey.spring.in.action.registry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * {@code BeanDefinitionRegistryHolder}
 *
 * @author photowey
 * @date 2021/12/03
 * @since 1.0.0
 */
@Component
public class BeanDefinitionRegistryHolder implements BeanDefinitionRegistryPostProcessor {

    private BeanDefinitionRegistry registry;
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public <T> void registerBean(String beanName, Class<T> clazz) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        this.registerBean(beanName, beanDefinition);
    }

    public void registerBean(String beanName, BeanDefinition beanDefinition) {
        this.registry.registerBeanDefinition(beanName, beanDefinition);
    }

    public <T> void registerBean(String beanName, T bean) {
        this.beanFactory.registerSingleton(beanName, bean);
    }

    public void unregisterBean(String beanName) {
        this.registry.removeBeanDefinition(beanName);
    }

    public <T> T getBean(String beanName) throws BeansException {
        return (T) this.beanFactory.getBean(beanName);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return this.beanFactory.getBean(requiredType);
    }

    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return this.beanFactory.getBean(beanName, requiredType);
    }
}
