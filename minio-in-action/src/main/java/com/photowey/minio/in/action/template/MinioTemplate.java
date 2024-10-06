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

import com.photowey.minio.in.action.exception.MinioException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * {@code MinioTemplate}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
public interface MinioTemplate {

    String DEFAULT_STREAM_CONTENT_TYPE = "application/octet-stream";

    // ----------------------------------------------------------------

    default boolean bucketNotExists(String bucket) {
        return !this.bucketExists(bucket);
    }

    boolean bucketExists(String bucket);

    boolean createBucket(String bucket);

    boolean removeBucket(String bucket);

    List<Bucket> buckets();

    // ----------------------------------------------------------------

    default ObjectWriteResponse putObject(String bucket, String object, InputStream in) {
        return this.call(() -> {
            return this.putObject(bucket, object, DEFAULT_STREAM_CONTENT_TYPE, in, in.available());
        });
    }

    default ObjectWriteResponse putObject(String bucket, String object, String contextType, InputStream in) {
        return this.call(() -> {
            return this.putObject(bucket, object, contextType, in, in.available());
        });
    }

    ObjectWriteResponse putObject(String bucket, String object, String contextType, InputStream in, long size);

    // ----------------------------------------------------------------

    InputStream downloadObject(String bucket, String object);

    default void downloadObject(String bucket, String object, String filename) {
        this.run(() -> {
            DownloadObjectArgs args = DownloadObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .filename(filename)
                .build();

            this.downloadObject(args);
        });
    }

    boolean downloadObject(DownloadObjectArgs args);

    // ----------------------------------------------------------------

    default StatObjectResponse statObject(String bucket, String object) {
        return this.call(() -> {
            StatObjectArgs args = StatObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build();

            return this.statObject(args);
        });
    }

    StatObjectResponse statObject(StatObjectArgs args);

    // -----------------------------------------------------------------

    default String url(String bucket, String object) {
        return this.call(() -> {
            return this.url(bucket, object, GetPresignedObjectUrlArgs.DEFAULT_EXPIRY_TIME);
        });
    }

    default String url(String bucket, String object, int expiry) {
        return this.call(() -> {
            return this.url(bucket, object, expiry, TimeUnit.SECONDS);
        });
    }

    default String url(String bucket, String object, int expiry, TimeUnit timeUnit) {
        return this.call(() -> {
            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(object)
                .expiry(expiry, timeUnit).
                build();

            return this.url(args);
        });
    }

    String url(GetPresignedObjectUrlArgs args);

    // -----------------------------------------------------------------

    default void run(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    default <T> T call(Callable<T> task) {
        try {
            return task.call();
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }
}
