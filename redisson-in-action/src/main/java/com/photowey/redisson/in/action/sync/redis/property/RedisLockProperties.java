/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.redisson.in.action.sync.redis.property;

import com.photowey.redisson.in.action.sync.redis.mode.RedisModeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@code RedisLockProperties}
 *
 * @author photowey
 * @date 2023/02/01
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.lock.redisson")
public class RedisLockProperties {

    private RedisModeEnum mode = RedisModeEnum.SINGLE;
    private String address = "redis://127.0.0.1:6379";
    private String password = null;
    private String masterName = "master";
    private String namespace = "";
    private int database = 0;
    private int timeout = 10_000;
    private boolean ignoreLockingExceptions = false;

}
