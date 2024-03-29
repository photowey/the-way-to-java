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
package com.photowey.minio.in.action.property;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * {@code MinioProperties}
 *
 * @author photowey
 * @date 2022/06/06
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.minio", ignoreUnknownFields = true)
public class MinioProperties implements InitializingBean {

    private String endpoint;
    private String region;
    private String accessKey;
    private String secretKey;

    private Sync sync = new Sync();
    private Async async = new Async();

    @Data
    public static class Sync {
        private boolean enabled;
    }

    @Data
    public static class Async {
        private boolean enabled;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.hasText(this.getEndpoint())) {
            throw new RuntimeException("spring.minio.endpoint can't be blank");
        }
        if (!StringUtils.hasText(this.getAccessKey())) {
            throw new RuntimeException("spring.minio.accessKey can't be blank");
        }
        if (!StringUtils.hasText(this.getSecretKey())) {
            throw new RuntimeException("spring.minio.secretKey can't be blank");
        }
    }
}
