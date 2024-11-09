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
package com.photowey.scheduled.in.action.core.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code NotifyResponse}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyResponse implements Serializable {

    private static final long serialVersionUID = 4671263872214620515L;

    private Long code;
    private String message;

    public static NotifyResponse walk(Long code, String message) {
        return NotifyResponse.builder()
            .code(code)
            .message(message)
            .build();
    }

    public static NotifyResponse ok() {
        return NotifyResponse.builder()
            .code(0L)
            .message("ok")
            .build();
    }

    public boolean isSuccessful() {
        return this.getCode() == null || this.getCode().equals(0L);
    }
}

