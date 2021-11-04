package com.photowey.dubbo.consumer.in.action.service.mock;

import com.photowey.dubbo.api.in.action.service.mock.MockService;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code MocServiceRpcExceptionHandler}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Slf4j
public class MocServiceRpcExceptionHandler implements MockService {

    @Override
    public String doMock(String parameter) {
        log.info("--- remote rpc exception.so do manual local mock ---");
        return "handle doMock,when rpc exception";
    }
}
