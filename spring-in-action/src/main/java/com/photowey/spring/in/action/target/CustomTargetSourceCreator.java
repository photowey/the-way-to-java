package com.photowey.spring.in.action.target;

import org.springframework.aop.framework.autoproxy.target.AbstractBeanFactoryBasedTargetSourceCreator;
import org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@code CustomTargetSourceCreator}
 *
 * @author photowey
 * @date 2021/11/16
 * @since 1.0.0
 */
public class CustomTargetSourceCreator extends AbstractBeanFactoryBasedTargetSourceCreator {

    @Override
    protected AbstractBeanFactoryBasedTargetSource createBeanFactoryBasedTargetSource(Class<?> beanClass, String beanName) {
        if (this.getBeanFactory() instanceof ConfigurableListableBeanFactory) {
            if (beanClass.isAssignableFrom(CustomTargetSourceBean.class)) {
                // 为指定的类返回 {@code CustomTargetSource} - 为了提前创建一个代理对象
                return new CustomTargetSource();
            }
        }

        return null;
    }
}

