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
package com.photowey.minio.in.action.template;

import com.photowey.minio.in.action.exception.MinioException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * {@code MinioTemplate}
 *
 * @author photowey
 * @date 2022/06/09
 * @since 1.0.0
 */
public class MinioTemplate {

    private static final String CONTENT_TYPE = "application/octet-stream";
    private final MinioClient minioClient;

    public MinioTemplate(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    // ---------------------------------------------------------------- exists

    public boolean bucketExists(String bucket) throws MinioException {
        try {
            return this.minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public boolean bucketNotExists(String bucket) throws MinioException {
        return !bucketExists(bucket);
    }

    // ---------------------------------------------------------------- create

    public void createBucket(String bucket) throws MinioException {
        try {
            if (this.bucketNotExists(bucket)) {
                this.minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public void removeBucket(String bucket) throws MinioException {
        try {
            this.minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public List<Bucket> listBuckets() throws MinioException {
        try {
            return this.minioClient.listBuckets();
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    // ---------------------------------------------------------------- put

    public void putObject(String bucket, String object, InputStream in) throws MinioException {
        try {
            this.putObject(bucket, object, in, in.available(), CONTENT_TYPE);
        } catch (Exception e) {
            throw new MinioException(e);
        }

    }

    public void putObject(String bucket, String object, InputStream in, long size, String contextType) throws MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket).
                    object(object)
                    .stream(in, size, -1)
                    .contentType(contextType)
                    .build();
            this.minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    // ---------------------------------------------------------------- download

    public InputStream downloadObject(String bucket, String object) throws MinioException {
        try {
            return this.minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(object).build());
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public void downloadObject(String bucket, String object, String file) throws MinioException {
        try {
            this.downloadObject(DownloadObjectArgs.builder().bucket(bucket).object(object).filename(file).build());
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public void downloadObject(DownloadObjectArgs args) throws MinioException {
        try {
            this.minioClient.downloadObject(args);
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    // ---------------------------------------------------------------- stat

    public StatObjectResponse statObject(String bucket, String object) throws MinioException {
        try {
            return this.statObject(StatObjectArgs.builder().bucket(bucket).object(object).build());
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public StatObjectResponse statObject(StatObjectArgs args) throws MinioException {
        try {
            return this.minioClient.statObject(args);
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    // ---------------------------------------------------------------- url

    public String url(String bucket, String object) throws MinioException {
        try {
            return this.url(bucket, object, GetPresignedObjectUrlArgs.DEFAULT_EXPIRY_TIME);
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public String url(String bucket, String object, int expiry) throws MinioException {
        try {
            return this.url(bucket, object, expiry, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public String url(String bucket, String object, int expiry, TimeUnit timeUnit) throws MinioException {
        try {
            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucket)
                    .object(object)
                    .expiry(expiry, timeUnit).
                    build();
            return this.url(args);
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }

    public String url(GetPresignedObjectUrlArgs args) throws MinioException {
        try {
            return this.minioClient.getPresignedObjectUrl(args);
        } catch (Exception e) {
            throw new MinioException(e);
        }
    }
}
