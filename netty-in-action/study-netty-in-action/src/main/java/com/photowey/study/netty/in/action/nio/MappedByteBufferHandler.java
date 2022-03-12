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

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@code MappedByteBuffer}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class MappedByteBufferHandler {

    String wait = "你曾在橄榄树下等待又等待，\n" +
            "我却在遥远的地方徘徊再徘徊，\n" +
            "人生本是一场迷藏的梦，\n" +
            "请莫对我责怪。\n" +
            "为把遗憾续回来，\n" +
            "我也去等待，\n" +
            "每当月圆时，\n" +
            "对着那橄榄树独自膜拜。\n" +
            "你永远不再来，\n" +
            "我永远在等待，\n" +
            "等待等待，\n" +
            "等待等待，\n" +
            "越等待，\n" +
            "我心中越爱。";

    public void mapped() {

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("oob.txt", "rw")) {
            FileChannel fileChannel = randomAccessFile.getChannel();

            // size == 5
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

            mappedByteBuffer.put(0, (byte) 'A');
            mappedByteBuffer.put(3, (byte) '9');

            // throw IndexOutOfBoundsException
            mappedByteBuffer.put(5, (byte) 'Z');

            System.out.println("修改成功~~");
        } catch (Exception e) {
            if (e instanceof IndexOutOfBoundsException) {
                throw (IndexOutOfBoundsException) e;
            }
        }
    }

}
