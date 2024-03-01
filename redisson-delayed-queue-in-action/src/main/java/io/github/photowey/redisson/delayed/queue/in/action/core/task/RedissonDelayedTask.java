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
package io.github.photowey.redisson.delayed.queue.in.action.core.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * {@code RedissonDelayedTask}
 *
 * @author photowey
 * @date 2024/03/01
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedissonDelayedTask<P extends Serializable> implements Serializable {

    private static final long serialVersionUID = -3410279574140030355L;

    /**
     * 任务标识
     * |- 处理自己感兴趣的任务
     */
    private String taskId;
    private P payload;

    private long delayed;
    private String timeUnit;

    public TimeUnit determineTimeUnit() {
        return this.determineTimeUnit(() -> TimeUnit.MILLISECONDS);
    }

    public TimeUnit determineTimeUnit(Supplier<TimeUnit> fx) {
        if (null == timeUnit) {
            return fx.get();
        }

        return TimeUnit.valueOf(this.timeUnit);
    }

    public <T> T getGenericPayload() {
        return (T) this.getPayload();
    }
}


