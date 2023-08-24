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
package com.photowey.dubbo.spi.in.action.spi.adaptive;

import com.alibaba.dubbo.common.extension.Activate;
import com.photowey.dubbo.spi.in.action.spi.activate.ActivateApi;

/**
 * {@code DubboActivate}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
// @Adaptive
// @SPI("dubbo") -> getDefaultExtension() -> No such extension com.photowey.dubbo.spi.in.action.spi.activate.ActivateApi by name dubbo
@Activate(order = 2, group = {"dubbo"})
public class DubboActivate implements ActivateApi {

    @Override
    public String doActivateApi(String parameter) {
        return String.format("%s-%s", "Dubbo", parameter);
    }
}
