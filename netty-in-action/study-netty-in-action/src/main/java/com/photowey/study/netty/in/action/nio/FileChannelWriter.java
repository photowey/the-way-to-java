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
package com.photowey.study.netty.in.action.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * {@code FileChannelWrite}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class FileChannelWriter {

    public void write() {

        String words = "诗，像清晨的一缕阳光，给我带来了温暖；\n" +
                "诗，像午后的一阵凉风，让我感到清爽惬意；\n" +
                "诗，更像一位良师益友，给了我很多教益。\n" +
                "在诗海中漫游，我看到了许多闪光的东西，\n" +
                "那便是人类的智慧，\n" +
                "我伸手捕捉，拾取诗中的谆谆细语。";

        try (FileOutputStream output = new FileOutputStream("poetry.txt");) {
            FileChannel outputChannel = output.getChannel();
            byte[] data = words.getBytes(StandardCharsets.UTF_8);
            ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);
            byteBuffer.put(data);

            byteBuffer.flip();

            outputChannel.write(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
