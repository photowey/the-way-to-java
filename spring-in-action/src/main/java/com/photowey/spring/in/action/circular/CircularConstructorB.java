package com.photowey.spring.in.action.circular;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * {@code CircularConstructorB}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Data
@Component
public class CircularConstructorB implements Serializable, SmartInitializingSingleton {

    private static final long serialVersionUID = 8876517903025011546L;

    private String name = "I'm CircularConstructorB~";

    private final CircularConstructorA circularConstructorA;

    // The dependencies of some of the beans in the application context form a cycle:
    @Lazy // 能解决循环依赖
    public CircularConstructorB(CircularConstructorA circularConstructorA) {
        this.circularConstructorA = circularConstructorA;
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("afterSingletonsInstantiated-->sayHello:{}", this.circularConstructorA.getName());
    }
}
