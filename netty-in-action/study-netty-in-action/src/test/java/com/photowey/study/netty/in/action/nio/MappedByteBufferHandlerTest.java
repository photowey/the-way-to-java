/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.study.netty.in.action.nio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code MappedByteBufferHandlerTest}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
class MappedByteBufferHandlerTest {

    @Test
    void testMapped() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            MappedByteBufferHandler bufferHandler = new MappedByteBufferHandler();
            bufferHandler.mapped();
        });
    }

}