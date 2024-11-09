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
package com.photowey.scheduled.in.action.property;

import com.photowey.common.in.action.formatter.StringFormatter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * {@code MessageProperties}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Data
@Validated
@ConfigurationProperties(prefix = MessageProperties.PREFIX)
public class MessageProperties implements Serializable {

    private static final long serialVersionUID = 3889968884890689514L;
    public static final String PREFIX = "io.github.photowey.platform.app.devops.message";

    private App app = new App();
    private Dingtalk dingtalk = new Dingtalk();

    public static String getPrefix() {
        return PREFIX;
    }

    @Data
    public static class App implements Serializable {

        private static final long serialVersionUID = -4406074525347571099L;

        // 校验

        private String host;
        private Integer port;
        private String protocol = "https";

        private String domain;

        public String tryAcquireDomain() {
            if (StringUtils.hasLength(this.getDomain())) {
                return this.getDomain();
            }

            return StringFormatter.format("{}://{}:{}", this.getProtocol(), this.getDomain(), this.getPort());
        }
    }

    @Data
    public static class Dingtalk implements Serializable {
        private boolean enabled = true;

        private String webhook;
        private String accessToken;
        private String appSecret;

        public boolean notEnabled() {
            return !this.isEnabled();
        }

        public String populateAPI(String fragment) {
            return String.format("%s?access_token=%s&%s", this.getWebhook(), this.getAccessToken(), fragment);
        }
    }

    public App app() {
        return app;
    }

    public Dingtalk dingtalk() {
        return dingtalk;
    }
}

