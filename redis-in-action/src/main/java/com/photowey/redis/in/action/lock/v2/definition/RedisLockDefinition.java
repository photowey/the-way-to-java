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
package com.photowey.redis.in.action.lock.v2.definition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code RedisLockDefinition}
 *
 * @author photowey
 * @date 2022/10/04
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisLockDefinition implements Serializable {

    private static final long serialVersionUID = 7393565940286021746L;

    /**
     * 业务唯一 key
     */
    private String businessKey;
    /**
     * 加锁时间 (秒 s)
     */
    private Long lockTime;
    /**
     * 上次更新时间（ms）
     */
    private Long lastModifyTime;
    /**
     * 保存当前线程
     */
    private Thread currentTread;
    /**
     * 总共尝试次数
     */
    private int tryCount;
    /**
     * 当前尝试次数
     */
    private int currentCount;
    /**
     * 更新的时间周期（毫秒）,公式 = 加锁时间（转成毫秒） / 3
     */
    private Long modifyPeriod;

    public RedisLockDefinition(String businessKey, Long lockTime, Long lastModifyTime, Thread currentTread, int tryCount) {
        this.businessKey = businessKey;
        this.lockTime = lockTime;
        this.lastModifyTime = lastModifyTime;
        this.currentTread = currentTread;
        this.tryCount = tryCount;
        this.modifyPeriod = lockTime * 1000 / 3;
    }
}