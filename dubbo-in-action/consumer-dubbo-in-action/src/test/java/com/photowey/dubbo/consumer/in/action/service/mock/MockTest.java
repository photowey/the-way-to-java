/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.dubbo.consumer.in.action.service.mock;

import com.alibaba.dubbo.config.annotation.Reference;
import com.photowey.dubbo.api.in.action.service.mock.MockService;
import com.photowey.dubbo.consumer.in.action.setup.AnnotationSetup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code MockTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)  // Junit5
@ContextConfiguration(classes = {AnnotationSetup.class})
public class MockTest {

    // call remote
    // @Reference(check = false, mock = "true")
    // @Reference(check = false, mock = "com.photowey.dubbo.consumer.in.action.service.mock.MocServiceRpcExceptionHandler")

    // call local
    @Reference(check = false, mock = "force:return force")
    private MockService mockService;

    @Test
    void testStub() {
        // Failed to invoke the method doMock in the service com.photowey.dubbo.api.in.action.service.mock.MockService.
        // Tried 3 times of the providers [192.168.137.1:20880] (1/1) from the registry 192.168.1.11:2182
        // on the consumer 192.168.137.1 using the dubbo version 2.6.9. Last error is: Invoke remote method timeout.
        // method: doMock, provider: dubbo://192.168.137.1:20880/com.photowey.dubbo.api.in.action.service.mock.MockService?
        // anyhost=true&application=consumer-dubbo-in-action&bean.name=ServiceBean:com.photowey.dubbo.api.in.action.service.mock.MockService&
        // check=false&default.check=false&dubbo=2.0.2&generic=false&interface=com.photowey.dubbo.api.in.action.service.mock.MockService&
        // methods=doMock&mock=true&owner=world&pid=9080&register.ip=192.168.137.1&remote.timestamp=1636038737516&
        // side=consumer&timestamp=1636038838537, cause: Waiting server-side response timeout.
        // start time: 2021-11-04 23:14:12.376, end time: 2021-11-04 23:14:13.380,
        // client elapsed: 1 ms, server elapsed: 1002 ms, timeout: 1000 ms, request:
        // Request [id=2, version=2.0.2, twoway=true, event=false, broken=false, data=RpcInvocation [methodName=doMock,
        // parameterTypes=[class java.lang.String], arguments=[doMock], attachments={path=com.photowey.dubbo.api.in.action.service.mock.MockService,
        // interface=com.photowey.dubbo.api.in.action.service.mock.MockService, version=0.0.0}]], channel: /192.168.137.1:61111 -> /192.168.137.1:20880

        // @Reference(check = false, mock = "true")
        // com.photowey.dubbo.api.in.action.service.mock.MockServiceMock - --- remote rpc exception.so do default local mock ---

        // @Reference(check = false, mock = "com.photowey.dubbo.consumer.in.action.service.mock.MocServiceRpcExceptionHandler")
        // com.photowey.dubbo.consumer.in.action.service.mock.MocServiceRpcExceptionHandler - --- remote rpc exception.so do manual local mock ---

        // @Reference(check = false, mock = "force:return force")
        // handle the doMock result is:[force]

        String doMock = this.mockService.doMock("doMock");
        log.info("handle the doMock result is:[{}]", doMock);
    }

}
