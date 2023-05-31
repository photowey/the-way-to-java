package com.photowey.mybatis.in.action.plugin.shared;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * {@code MybatisScanner}
 *
 * @author photowey
 * @date 2023/05/31
 * @since 1.0.0
 */

@Component
@Lazy
public class MybatisScanner implements BeanDefinitionRegistryPostProcessor {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    /**
     * 触发 mybatis 重新扫描 mapper
     */
    public void scanner() {
        MapperScannerConfigurer mapperScannerConfigurer = SpringUtil.getBean(MapperScannerConfigurer.class);

        mapperScannerConfigurer.postProcessBeanDefinitionRegistry(this.beanDefinitionRegistry);
    }
}