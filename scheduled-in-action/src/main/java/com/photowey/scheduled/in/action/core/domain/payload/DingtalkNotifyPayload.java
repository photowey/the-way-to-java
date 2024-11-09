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
package com.photowey.scheduled.in.action.core.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code DingtalkNotifyPayload}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DingtalkNotifyPayload implements Serializable {

    /**
     * 主题标识
     */
    private String topicId;
    /**
     * 主题
     */
    private String topic;
}

