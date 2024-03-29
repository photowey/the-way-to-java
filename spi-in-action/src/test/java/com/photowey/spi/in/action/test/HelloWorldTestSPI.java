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
package com.photowey.spi.in.action.test;

import com.photowey.spi.in.action.core.annotation.SPI;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code HelloWorldTestSPI}
 *
 * @author photowey
 * @date 2023/11/04
 * @since 1.0.0
 */
@Slf4j
@SPI(value = "hello")
public class HelloWorldTestSPI implements HelloTestSPI {

    @Override
    public String sayHello() {
        return "say hello world!";
    }

    @Override
    public void init() {
        log.info("init the HelloWorldTestSPI");
    }
}
