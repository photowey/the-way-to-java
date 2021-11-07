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
package com.photowey.rocketmq.in.action.mmap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * {@code MmapHandler}
 *
 * @author photowey
 * @date 2021/11/01
 * @since 1.0.0
 */
@Slf4j
@Component
public class MmapHandler {

    private static final String MAPPED_FILE_FOLDER = "D:\\mmap";

    public String mmap(final String fileName, final String content) throws IOException {
        File file = new File(MAPPED_FILE_FOLDER, fileName);

        ensureDirOK(file.getParent());

        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
        MappedByteBuffer mmap = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        mmap.put(bytes);
        mmap.flip();

        byte[] bb = new byte[bytes.length];
        mmap.get(bb, 0, bytes.length);

        String readResult = new String(bb, StandardCharsets.UTF_8);

        // unmap(mmap);
        clean(mmap);

        return readResult;
    }

    public void write(final String fileName, final String content) throws IOException {
        File file = new File(fileName);
        ensureDirOK(file.getParent());
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        try (FileChannel channel = raf.getChannel()) {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
            channel.force(true);
        }
    }

    /**
     * <pre>
     *     exception: class com.photowey.rocketmq.in.action.mmap.MmapHandler (in unnamed module @0x1c6c6f24)
     *     cannot access class jdk.internal.ref.Cleaner (in module java.base) because module java.base does not export jdk.internal.ref to unnamed module @0x1c6c6f24
     *     JVM OPTIONS: --add-exports java.base/jdk.internal.ref=ALL-UNNAMED
     * </pre>
     *
     * @param bb {@link MappedByteBuffer}
     */
    public static void unmap(MappedByteBuffer bb) {
        /*Cleaner cleaner = ((DirectBuffer) bb).cleaner();
        if (null != cleaner) {
            cleaner.clean();
        }*/

        // TODO 打包不报错
        clean(bb);
    }

    /**
     * <pre>
     *     java.lang.reflect.InaccessibleObjectException: Unable to make public void jdk.internal.ref.Cleaner.clean() accessible:
     *     module java.base does not "exports jdk.internal.ref" to unnamed module @6dfcffb5
     *     JVM OPTIONS: --add-exports java.base/jdk.internal.ref=ALL-UNNAMED
     * </pre>
     *
     * @param buffer {@link ByteBuffer}
     * @copyfrom {@code org.apache.rocketmq.store.MappedFile#clean}
     */
    public static void clean(final ByteBuffer buffer) {
        if (buffer == null || !buffer.isDirect() || buffer.capacity() == 0)
            return;
        invoke(invoke(viewed(buffer), "cleaner"), "clean");
    }

    private static Object invoke(final Object target, final String methodName, final Class<?>... args) {
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = method(target, methodName, args);
                    method.setAccessible(true);
                    return method.invoke(target);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    private static Method method(Object target, String methodName, Class<?>[] args)
            throws NoSuchMethodException {
        try {
            return target.getClass().getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            return target.getClass().getDeclaredMethod(methodName, args);
        }
    }

    private static ByteBuffer viewed(ByteBuffer buffer) {
        String methodName = "viewedBuffer";
        Method[] methods = buffer.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("attachment")) {
                methodName = "attachment";
                break;
            }
        }

        ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
        if (viewedBuffer == null)
            return buffer;
        else
            return viewed(viewedBuffer);
    }

    public static void ensureDirOK(final String dirName) {
        if (null != dirName) {
            File f = new File(dirName);
            if (!f.exists()) {
                boolean result = f.mkdirs();
                log.info(dirName + " mkdir " + (result ? "OK" : "Failed"));
            }
        }
    }
}
