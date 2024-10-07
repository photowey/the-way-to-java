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
package com.photowey.minio.in.action.template;

import com.photowey.minio.in.action.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * {@code MinioTemplateTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
@Slf4j
@SpringBootTest
class MinioTemplateTest extends AbstractTest {

    @Test
    void testBuckets() {
        boolean exists = this.minioTemplate.bucketExists("openio.dev");
        Assertions.assertTrue(exists);
    }

    @Test
    void testUpload() throws Exception {
        Resource resource = new ClassPathResource("pictures/sky.jpg");
        String bucket = "openio.dev";
        String object = "sky.jpg";
        this.minioTemplate.putObject(bucket, object, resource.getInputStream());
        String url = this.minioTemplate.url(bucket, object, 1, TimeUnit.MINUTES);
        // http://127.0.0.1:9000/openio.dev/sky.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=uSloqshPrpXwZmGei6Gj%2F20241006%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241006T075933Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=fb8c59f482437ce71f286b0242b7cfb719cc879b2e218f43a30f5abe0a9ff027
        log.info("the MinIO access url is: {}", url);
    }

    @Test
    void testDownload() throws IOException {
        String bucket = "openio.dev";
        String object = "sky.jpg";
        try (InputStream stream = this.minioTemplate.downloadObject(bucket, object)) {
            String dir = System.getProperty("user.dir");
            if (!dir.endsWith(File.separator)) {
                dir += File.separator;
            }

            String tmpFile = dir + "sky.jpg";
            this.write(tmpFile, true, stream.readAllBytes());

            File file = new File(tmpFile);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void write(final String target, boolean quiet, final byte[] data) {
        try (RandomAccessFile raf = new RandomAccessFile(target, "rw");
             FileChannel channel = raf.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
            channel.force(true);
        } catch (Exception e) {
            if (!quiet) {
                throw new RuntimeException(e);
            }
        }
    }
}
