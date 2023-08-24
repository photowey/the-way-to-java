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
package com.photowey.delayqueue.in.action.netty;

import com.alibaba.fastjson2.JSON;
import com.photowey.delayqueue.in.action.shared.io.netty.util.Timeout;
import com.photowey.delayqueue.in.action.shared.io.netty.util.TimerTask;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.util.StopWatch;

/**
 * {@code AbstractNettyDelayedQueueListener}
 *
 * @author photowey
 * @date 2023/03/25
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractSharedNettyDelayedQueueListener<T> implements TimerTask {

    private static final String GROUP = "delay-queue-group";
    private final IdGenerator generator = new AlternativeJdkIdGenerator();
    @Getter
    @Setter
    private Task<T> task;

    @Override
    public void run(Timeout timeout) throws Exception {
        try {
            StopWatch watch = new StopWatch(GROUP);
            if (log.isInfoEnabled()) {
                watch.start("t1");
                log.info("Listen the netty delay queue,prepare run task:[{}]", task.getId());
            }
            this.execute(task);
            if (log.isInfoEnabled()) {
                watch.stop();
                log.info("Listen the netty delay queue,post run task:[{}],cast:[{} ms]", task.getId(), watch.getTotalTimeMillis());
            }
        } catch (Throwable e) {
            log.error("Listen the netty delay queue task:[{}] error", JSON.toJSONString(task), e);
            // retry
        }
    }

    /**
     * 执行任务
     *
     * @param task {@link Task<T>} 任务
     */
    protected abstract void execute(Task<T> task);

    public void buildTask(T data) {
        this.setTask(Task.<T>builder().id(this.taskId()).data(data).build());
    }

    private String taskId() {
        return this.generator.generateId().toString();
    }
}
