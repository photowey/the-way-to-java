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
package com.photowey.commom.in.action.util;

import com.photowey.commom.in.action.thrower.AssertionErrorThrower;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * {@code FileUtils}
 *
 * @author photowey
 * @date 2022/11/23
 * @since 1.0.0
 */
public final class FileUtils {

    private FileUtils() {
        // utility class; can't create
        AssertionErrorThrower.throwz(FileUtils.class);
    }

    public static void write(final String target, final String data) {
        write(target, data.getBytes(StandardCharsets.UTF_8));
    }

    public static void write(final String target, final String data, boolean quiet) {
        write(target, quiet, data.getBytes(StandardCharsets.UTF_8));
    }

    public static void write(final String target, final byte[] data) {
        write(target, false, data);
    }

    /**
     * 注意: 当前启动用户的 {@code rwx} 权限
     */
    public static void write(final String target, boolean quiet, final byte[] data) {
        try {
            RandomAccessFile raf = new RandomAccessFile(target, "rw");
            try (FileChannel channel = raf.getChannel()) {
                ByteBuffer buffer = ByteBuffer.allocate(data.length);
                buffer.put(data);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    channel.write(buffer);
                }
                channel.force(true);
            }
        } catch (IOException e) {
            if (!quiet) {
                throw new RuntimeException(String.format("write data into target file:%s exception", target), e);
            }
        }
    }

    public static String read(final String target) {
        return read(target, each -> !each.startsWith("#"));
    }

    public static String read(final String target, Predicate<String> filter) {
        return read(target, false, filter);
    }

    /**
     * Notes:
     * file in jar?
     *
     * @param target
     * @param quiet
     * @param filter
     * @return
     */
    public static String read(final String target, boolean quiet, Predicate<String> filter) {
        try {
            return Files.readAllLines(Paths.get(ClassLoader.getSystemResource(target).toURI()))
                    .stream()
                    .filter(filter)
                    .map(each -> each + System.lineSeparator())
                    .collect(Collectors.joining());
        } catch (Exception e) {
            if (!quiet) {
                throw new RuntimeException(String.format("read the target file:%s exception", target));
            }
        }

        return "";
    }
}
