package com.photowey.dubbo.consumer.in.action.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.photowey.dubbo.api.in.action.service.user.UserService;
import com.photowey.dubbo.consumer.in.action.service.ConsumerService;
import org.springframework.stereotype.Component;

/**
 * {@code ConsumerServiceImpl}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Component
public class ConsumerServiceImpl implements ConsumerService {

    @Reference
    private UserService userService;

    @Override
    public String sayHello(String name) {
        return this.userService.sayHello(name);
    }
}
