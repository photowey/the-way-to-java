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
package com.photowey.translator.util;

import com.photowey.translator.constant.TranslatorConstants;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@code FileUtils}
 *
 * @author photowey
 * @date 2022/10/16
 * @since 1.0.0
 */
public final class FileUtils {

    private FileUtils() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    public static void writeFile(final String fullFileName, final byte[] contents) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fullFileName, "rw");
            try (FileChannel channel = raf.getChannel()) {
                ByteBuffer buffer = ByteBuffer.allocate(contents.length);
                buffer.put(contents);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    channel.write(buffer);
                }
                channel.force(true);
            }
        } catch (IOException ignored) {
        }
    }

    public static String readJson(final String dir, String file) {
        try {
            Path dp = Paths.get(dir);
            Path fp = Paths.get(dp.toString(), file);
            return String.join(TranslatorConstants.STRING_EMPTY, Files.readAllLines(fp));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}