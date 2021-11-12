package com.photowey.spring.in.action.circular;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * {@code CircularFactoryBeanA}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Component
public class CircularFactoryBeanA implements FactoryBean<FactoryBeanA> {

    @Override
    public FactoryBeanA getObject() throws Exception {
        return new FactoryBeanA();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanA.class;
    }
}
