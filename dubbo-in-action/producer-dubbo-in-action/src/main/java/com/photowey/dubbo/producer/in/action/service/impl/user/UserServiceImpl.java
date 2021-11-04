package com.photowey.dubbo.producer.in.action.service.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.photowey.dubbo.api.in.action.service.user.UserService;

/**
 * {@code UserServiceImpl}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String sayHello(String name) {
        return String.format("Say hello, the name is:%s", name);
    }
}
