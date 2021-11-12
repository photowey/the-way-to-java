package com.photowey.spring.in.action.bridged;

import org.springframework.stereotype.Component;

/**
 * {@code SubClass}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Component
public class SubClass implements SuperClass<String> {

    @Override
    public String sayHello(String who) {
        return "Say hello:" + who;
    }
}
