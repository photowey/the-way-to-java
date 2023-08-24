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
package com.photowey.crypto.in.action.sm4;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code SM4UtilsTest}
 *
 * @author photowey
 * @date 2022/11/29
 * @since 1.0.0
 */
class SM4UtilsTest {

    @Test
    @SneakyThrows
    void testSM4() {
        String mobile = "13993993939";
        String key = "65C00054F4B4F1EFAC2CB0F62445CD32";
        String cipher = SM4Utils.encryptEcb(key, mobile);
        Assertions.assertTrue(SM4Utils.verifyEcb(key, cipher, mobile));
    }
}
