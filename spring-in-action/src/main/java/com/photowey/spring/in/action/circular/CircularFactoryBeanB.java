package com.photowey.spring.in.action.circular;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * {@code CircularFactoryBeanB}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Component
public class CircularFactoryBeanB implements FactoryBean<FactoryBeanB> {

    @Override
    public FactoryBeanB getObject() throws Exception {
        return new FactoryBeanB();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanB.class;
    }
}