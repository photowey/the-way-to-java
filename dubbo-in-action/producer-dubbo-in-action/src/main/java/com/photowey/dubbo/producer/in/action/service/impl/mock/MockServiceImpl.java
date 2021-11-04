package com.photowey.dubbo.producer.in.action.service.impl.mock;

import com.alibaba.dubbo.config.annotation.Service;
import com.photowey.dubbo.api.in.action.service.mock.MockService;

import java.util.Random;

/**
 * {@code MockServiceImpl}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Service
public class MockServiceImpl implements MockService {

    @Override
    public String doMock(String parameter) {
        Random random = new Random();
        try {
            int i = random.nextInt(10);
            if (true) {
                Thread.sleep(1000_000_000);
            }
        } catch (Exception e) {
        }
        return String.format("handle Mock action, the parameter is:%s", parameter);
    }
}
