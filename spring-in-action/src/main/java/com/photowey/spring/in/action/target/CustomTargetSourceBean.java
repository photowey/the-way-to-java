package com.photowey.spring.in.action.target;

import org.springframework.stereotype.Component;

/**
 * {@code CustomTargetSourceBean}
 *
 * @author photowey
 * @date 2021/11/16
 * @since 1.0.0
 */
@Component
public class CustomTargetSourceBean {

    public String sayHello() {
        return "Say hello from:CustomTargetSourceBean";
    }
}
