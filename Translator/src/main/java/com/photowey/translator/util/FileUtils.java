package com.photowey.translator.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readYaml(final String yamlFile) {
        try {
            return Files.readAllLines(Paths.get(ClassLoader.getSystemResource(yamlFile).toURI()))
                    .stream().filter(each -> !each.startsWith("#")).map(each -> each + System.lineSeparator()).collect(Collectors.joining());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String readProperties(final String propertiesFile) {
        try {
            return Files.readAllLines(Paths.get(ClassLoader.getSystemResource(propertiesFile).toURI()))
                    .stream().filter(each -> !each.startsWith("#")).map(each -> each + System.lineSeparator()).collect(Collectors.joining());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String readJson(final String jsonFile) {
        try {
            return String.join("", Files.readAllLines(Paths.get(ClassLoader.getSystemResource(jsonFile).toURI())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}