/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.core.enums;

/**
 * {@code AuthcenterDictionary}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/07
 */
public enum AuthcenterDictionary {
    ;

    public enum UserStatus {

        // 用户状态 1: 未激活(默认) 2: 已激活 4: 已冻结 8: 已禁止 16: 已过期

        UNACTIVATED("未激活", 1),
        ACTIVATED("已激活", 2),
        FREEZE("已冻结", 4),
        FORBIDDEN("已禁止", 8),
        EXPIRED("已过期", 16),

        ;

        private final String name;
        private final int value;

        UserStatus(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public enum AuthorizeStatus {

        // 授权状态 0: 未授权 1: 已授权

        UNAUTHORIZED("未授权", 0),
        AUTHORIZED("已授权", 1),

        ;

        private final String name;
        private final int value;

        AuthorizeStatus(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

}
