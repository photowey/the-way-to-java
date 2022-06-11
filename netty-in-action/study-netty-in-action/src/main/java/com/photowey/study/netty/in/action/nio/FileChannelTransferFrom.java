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
package com.photowey.study.netty.in.action.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * {@code FileChannelTransferFrom}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
public class FileChannelTransferFrom {

    public void transferFrom() {
        try (FileInputStream input = new FileInputStream("doc/lp-one-more-light.jpg");
             FileOutputStream output = new FileOutputStream("doc/lp-one-more-light-read-write.jpg");) {

            FileChannel inputChannel = input.getChannel();
            FileChannel outputChannel = output.getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            inputChannel.close();
            outputChannel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
