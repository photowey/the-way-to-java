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
package com.photowey.crypto.in.action.reader;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code ClassPathReaderTest}
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
@Slf4j
class ClassPathReaderTest {

    @Test
    void testRead() {
        String hello = ClassPathReader.joinRead("hello.txt");
        String keyHello = ClassPathReader.joinRead("key/hello.txt");

        Assertions.assertEquals("say hello", hello);
        Assertions.assertEquals("say key.hello", keyHello);

    }

}
