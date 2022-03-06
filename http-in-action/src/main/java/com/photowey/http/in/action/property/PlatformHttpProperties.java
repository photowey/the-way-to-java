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
package com.photowey.http.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * {@code PlatformHttpProperties}
 *
 * @author photowey
 * @date 2022/03/01
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "request.http")
public class PlatformHttpProperties implements Serializable {

    private static final long serialVersionUID = -5453604230161791240L;

    private HttpClient httpClient = new HttpClient();
    private OkHttp okHttp = new OkHttp();

    /**
     * 单位 统一为秒(s)
     */

    /**
     * {@code ApacheHttpClient}
     */
    @Data
    public static class HttpClient implements Serializable {

        private static final long serialVersionUID = 7282662461850242912L;

        private boolean enabled = false;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;
    }

    @Data
    public static class OkHttp implements Serializable {

        private static final long serialVersionUID = 474081402674142730L;

        private boolean enabled = true;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;
    }
}
