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
package com.photowey.redis.in.action.lock;

import lombok.Getter;

/**
 * {@code LockItem}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Getter
public class LockItem {

    private final String key;
    private final String value;
    // true: delay
    // false: clear
    private final boolean delayed;

    private final Thread owner;

    public LockItem(String key, String value, boolean delayed, Thread owner) {
        this.key = key;
        this.value = value;
        this.delayed = delayed;
        this.owner = owner;
    }
}
