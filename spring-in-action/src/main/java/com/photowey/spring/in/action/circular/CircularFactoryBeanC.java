package com.photowey.spring.in.action.circular;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * {@code CircularFactoryBeanC}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Component
public class CircularFactoryBeanC implements FactoryBean<FactoryBeanC> {

    @Override
    public FactoryBeanC getObject() throws Exception {
        return new FactoryBeanC();
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanC.class;
    }
}