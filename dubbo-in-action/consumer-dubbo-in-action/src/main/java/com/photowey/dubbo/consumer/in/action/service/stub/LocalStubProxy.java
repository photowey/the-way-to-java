package com.photowey.dubbo.consumer.in.action.service.stub;

import com.photowey.dubbo.api.in.action.service.stub.StubService;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code LocalStubProxy}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Slf4j
public class LocalStubProxy implements StubService {

    private StubService stubService;

    public LocalStubProxy(StubService stubService) {
        this.stubService = stubService;
    }

    @Override
    public String doStub(String parameter) {
        // 1.本地执行
        log.info("------------------------------------ 1.do local");
        try {
            // 2.远程调用
            log.info("------------------------------------ 2.do remote");
            return this.stubService.doStub(parameter);
        } catch (Exception e) {
            // 3.执行降级
            log.info("------------------------------------ 3.do with exception");
            log.error("handle the remote rpc exception,Hystrix", e);
        }

        // 4.继续执行
        log.info("------------------------------------ 3.do after exception");

        // 5.返回
        return "Like Hystrix~";
    }
}
