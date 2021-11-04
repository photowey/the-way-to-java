package com.photowey.dubbo.producer.in.action.service.impl.stub;

import com.alibaba.dubbo.config.annotation.Service;
import com.photowey.dubbo.api.in.action.service.stub.StubService;

/**
 * {@code StubServiceImpl}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Service
public class StubServiceImpl implements StubService {

    @Override
    public String doStub(String parameter) {
        return String.format("handle Stub action, the parameter is:%s", parameter);
    }
}
