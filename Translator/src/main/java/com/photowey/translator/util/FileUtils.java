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