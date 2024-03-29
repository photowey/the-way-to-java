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
package com.photowey.wechat.sdk.core.exception;

import com.photowey.wechat.sdk.core.formatter.StringFormatter;
import lombok.*;
import org.springframework.http.ResponseEntity;

/**
 * {@code WechatException}
 *
 * @author photowey
 * @date 2023/05/13
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WechatException extends RuntimeException {

    private String message;
    private ResponseEntity<?> response;

    public WechatException(String message, Object... args) {
        super(StringFormatter.format(message, args));
        this.message = StringFormatter.format(message, args);
    }

    public WechatException(Throwable cause, String message, Object... args) {
        super(StringFormatter.format(message, args), cause);
        this.message = StringFormatter.format(message, args);
    }
}
