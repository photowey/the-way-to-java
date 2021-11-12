package com.photowey.spring.in.action.circular;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * {@code CircularConstructorA}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Data
@Component
public class CircularConstructorA {

    private String name = "I'm CircularConstructorA~";

    private final CircularConstructorB circularConstructorB;

    public CircularConstructorA(CircularConstructorB circularConstructorB) {
        this.circularConstructorB = circularConstructorB;
    }
}
