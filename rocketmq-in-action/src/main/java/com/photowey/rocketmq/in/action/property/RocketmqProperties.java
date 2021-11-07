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
package com.photowey.rocketmq.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code RocketmqProperties}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.rocketmq", ignoreUnknownFields = true)
public class RocketmqProperties {

    private String host = "localhost";
    private Integer port = 9876;
    private String nameServer = String.format("%s:%s", this.getHost(), this.getPort());
}
