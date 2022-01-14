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
package com.photowey.knife4j.in.action.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * {@code Knife4jStarterProperties}
 *
 * @author photowey
 * @date 2022/01/14
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "api.knife4j.starter")
public class Knife4jStarterProperties implements Serializable {

    private static final long serialVersionUID = -7992540227934302975L;

    private String appDocket;
}
