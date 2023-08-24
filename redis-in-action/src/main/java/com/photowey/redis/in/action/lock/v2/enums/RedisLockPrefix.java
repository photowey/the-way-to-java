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
package com.photowey.redis.in.action.lock.v2.enums;

/**
 * {@code RedisLockPrefix}
 *
 * @author photowey
 * @date 2022/10/04
 * @since 1.0.0
 */
public enum RedisLockPrefix {

    ORDER("photowey:redis:loker:order", "order"),

    PAYMENT("photowey:redis:loker:payment", "payment");

    private String code;
    private String desc;

    RedisLockPrefix(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public String getUniqueKey(String key) {
        return String.format("%s:%s", this.code(), key);
    }
}
