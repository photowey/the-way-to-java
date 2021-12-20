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
package com.photowey.emqtt.in.action.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code MqttProperties}
 *
 * @author photowey
 * @date 2021/12/20
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.mqtt", ignoreUnknownFields = false)
public class MqttProperties {

    private String protocol;
    private String host;
    private Integer port;
    private String address;
    private String userName;
    private String password;
}
