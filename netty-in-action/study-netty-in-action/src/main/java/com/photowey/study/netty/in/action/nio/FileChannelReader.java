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

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@code FileChannelWrite}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class FileChannelReader {

    public void read() {
        File file = new File("poetry.txt");
        try (FileInputStream input = new FileInputStream(file)) {
            FileChannel inputChannel = input.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
            inputChannel.read(byteBuffer);
            System.out.println("----------------------------- read succeed -----------------------------");
            System.out.println(new String(byteBuffer.array()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
