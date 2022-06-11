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
package com.photowey.dubbo.producer.in.action.service.impl.callback;

import com.alibaba.dubbo.config.annotation.Argument;
import com.alibaba.dubbo.config.annotation.Method;
import com.alibaba.dubbo.config.annotation.Service;
import com.photowey.dubbo.api.in.action.service.callback.CallbackListener;
import com.photowey.dubbo.api.in.action.service.callback.CallbackService;

/**
 * {@code CallbackServiceImpl}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@Service(methods = {@Method(name = "sayHelloCallback", arguments = {@Argument(index = 1, callback = true)})})
public class CallbackServiceImpl implements CallbackService {

    @Override
    public String sayHello(String name) {
        return String.format("Say hello, the name is:%s", name);
    }

    @Override
    public void sayHelloCallback(String name, CallbackListener callback) {
        String result = String.format("Say hello, the name is:%s", name);
        callback.onCompleted(result);
    }
}
