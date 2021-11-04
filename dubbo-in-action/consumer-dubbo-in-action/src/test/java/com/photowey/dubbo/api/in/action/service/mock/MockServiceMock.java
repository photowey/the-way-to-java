package com.photowey.dubbo.api.in.action.service.mock;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code MockServiceMock}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Slf4j
public class MockServiceMock implements MockService {

    @Override
    public String doMock(String parameter) {
        log.info("--- remote rpc exception.so do default local mock ---");
        return "remote rpc exception.so do local mock return";
    }
}
