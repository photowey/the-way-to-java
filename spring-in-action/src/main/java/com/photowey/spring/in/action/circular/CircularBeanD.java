package com.photowey.spring.in.action.circular;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * {@code CircularBeanC}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Data
@Component
public class CircularBeanD implements Serializable, SmartInitializingSingleton {

    private static final long serialVersionUID = -6832262691617426207L;

    private String name = "I'm CircularBeanD~";

    @Autowired
    private CircularBeanA circularBeanA;

    @Autowired
    private FactoryBeanC factoryBeanC;

    @Override
    public void afterSingletonsInstantiated() {
        log.info("sayHello {}", this.getName());
    }
}
