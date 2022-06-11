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
package com.photowey.xxl.job.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code XxlJobProperties}
 *
 * @author photowey
 * @date 2022/04/11
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "xxl.job", ignoreUnknownFields = true)
public class XxlJobProperties {

    private String accessToken;
    private Admin admin = new Admin();
    private Executor executor = new Executor();

    @Data
    public static class Admin {
        private String addresses;
    }

    @Data
    public static class Executor {
        private String address;
        private String appName;
        private String ip;
        private String logPath;
        private Integer logRetentionDays = 30;
        private Integer port = 9999;
    }
}
