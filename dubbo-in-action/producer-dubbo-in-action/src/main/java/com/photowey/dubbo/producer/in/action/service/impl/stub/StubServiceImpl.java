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
