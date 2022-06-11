/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
