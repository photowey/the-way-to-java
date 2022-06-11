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
