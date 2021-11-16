package com.photowey.spring.in.action.target;

import org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;

/**
 * {@code CustomTargetSource}
 *
 * @author photowey
 * @date 2021/11/16
 * @since 1.0.0
 */
public class CustomTargetSource extends AbstractBeanFactoryBasedTargetSource {

    @Override
    public Object getTarget() throws Exception {
        // 这是一个多例, 同时 BeanFactory 也是一个新的
        return this.getBeanFactory().getBean(this.getTargetBeanName());
    }
}
