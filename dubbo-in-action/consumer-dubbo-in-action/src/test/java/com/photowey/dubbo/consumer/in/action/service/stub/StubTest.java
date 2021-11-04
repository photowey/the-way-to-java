package com.photowey.dubbo.consumer.in.action.service.stub;

import com.alibaba.dubbo.config.annotation.Reference;
import com.photowey.dubbo.api.in.action.service.stub.StubService;
import com.photowey.dubbo.consumer.in.action.setup.AnnotationSetup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code StubTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)  // Junit5
@ContextConfiguration(classes = {AnnotationSetup.class})
public class StubTest {

    @Reference(check = false, stub = "com.photowey.dubbo.consumer.in.action.service.stub.LocalStubProxy")
    private StubService stubService;

    @Test
    void testStub() {
        String doStub = this.stubService.doStub("doStub");
        Assertions.assertEquals("handle Stub action, the parameter is:doStub", doStub);
    }

}
