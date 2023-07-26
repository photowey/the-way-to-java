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
package com.photowey.nio.in.action.pipe;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code PipeTest}
 *
 * @author photowey
 * @date 2023/07/23
 * @since 1.0.0
 */
class PipeTest {

    @Test
    void testPipe() throws Exception {
        Pipe pipe = Pipe.open();

        Pipe.SinkChannel sinkChannel = pipe.sink();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        byteBuffer.put(formatter.format(LocalDateTime.now()).getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();

        sinkChannel.write(byteBuffer);

        Pipe.SourceChannel sourceChannel = pipe.source();
        byteBuffer.flip();

        int length = sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), 0, length));

        sourceChannel.close();
        sinkChannel.close();
    }

}
