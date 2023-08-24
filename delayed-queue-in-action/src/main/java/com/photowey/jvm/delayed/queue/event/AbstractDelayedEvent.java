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
package com.photowey.jvm.delayed.queue.event;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * {@code AbstractDelayedEvent}
 * 延迟队列事件
 *
 * @author photowey
 * @date 2022/08/07
 * @since 1.0.0
 */
@Data
public abstract class AbstractDelayedEvent<D> implements Serializable, DelayedEvent<D> {

    /**
     * 为什么: eventId 和 eventType 设计成字符串?
     * <p>
     * 这样根据有灵活性 -> 开发者可以根据实际情况设置具体的值,
     * 在适当的时候通过 {@link DelayedEvent#transfer(java.util.function.Function)} 做一次转换转换
     * 也是不错的选择
     */
    protected String eventId;
    protected String eventType;
    /**
     * 期望执行的时间
     * 也就是: 出队的时间 单位: ms
     */
    protected Long runAt;
    protected Object data;
    protected Integer enqueueTimes;

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.getRunAt() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        AbstractDelayedEvent<D> delayedEvent = (AbstractDelayedEvent<D>) (delayed);
        long diff = this.getDelay(TimeUnit.MILLISECONDS) - (delayedEvent.getDelay(TimeUnit.MILLISECONDS));
        return (diff == 0) ? 0 : ((diff > 0) ? 1 : -1);
    }
}
