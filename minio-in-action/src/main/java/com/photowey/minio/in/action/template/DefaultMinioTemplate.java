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

import io.minio.*;
import io.minio.messages.Bucket;

import java.io.InputStream;
import java.util.List;

/**
 * {@code DefaultMinioTemplate}
 *
 * @author photowey
 * @date 2022/06/09
 * @since 1.0.0
 */
public class DefaultMinioTemplate implements MinioTemplate {

    private final MinioClient minioClient;

    public DefaultMinioTemplate(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    // ----------------------------------------------------------------- bucket

    @Override
    public boolean bucketExists(String bucket) {
        return this.call(() -> {
            BucketExistsArgs args = BucketExistsArgs.builder()
                .bucket(bucket)
                .build();

            return this.minioClient.bucketExists(args);
        });
    }

    // -----------------------------------------------------------------

    @Override
    public boolean createBucket(String bucket) {
        return this.call(() -> {
            if (this.bucketExists(bucket)) {
                return false;
            }

            MakeBucketArgs args = MakeBucketArgs.builder()
                .bucket(bucket)
                .build();
            this.minioClient.makeBucket(args);

            return true;
        });
    }

    @Override
    public boolean removeBucket(String bucket) {
        return this.call(() -> {
            RemoveBucketArgs args = RemoveBucketArgs.builder()
                .bucket(bucket)
                .build();
            this.minioClient.removeBucket(args);

            return true;
        });
    }

    @Override
    public List<Bucket> buckets() {
        return this.call(this.minioClient::listBuckets);
    }

    // -----------------------------------------------------------------

    @Override
    public ObjectWriteResponse putObject(String bucket, String object, String contextType, InputStream in, long size) {
        return this.call(() -> {
            PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .stream(in, size, -1)
                .contentType(contextType)
                .build();

            return this.minioClient.putObject(args);
        });
    }

    // -----------------------------------------------------------------

    @Override
    public InputStream downloadObject(String bucket, String object) {
        return this.call(() -> {
            GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build();

            return this.minioClient.getObject(args);
        });
    }

    @Override
    public boolean downloadObject(DownloadObjectArgs args) {
        return this.call(() -> {
            this.minioClient.downloadObject(args);

            return true;
        });
    }

    // -----------------------------------------------------------------

    @Override
    public StatObjectResponse statObject(StatObjectArgs args) {
        return this.call(() -> {
            return this.minioClient.statObject(args);
        });
    }

    // -----------------------------------------------------------------

    @Override
    public String url(GetPresignedObjectUrlArgs args) {
        return this.call(() -> {
            return this.minioClient.getPresignedObjectUrl(args);
        });
    }
}
