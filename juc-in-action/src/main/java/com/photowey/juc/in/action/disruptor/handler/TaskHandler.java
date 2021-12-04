/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.juc.in.action.disruptor.handler;

import com.lmax.disruptor.WorkHandler;
import com.photowey.juc.in.action.disruptor.event.DataEvent;
import com.photowey.juc.in.action.disruptor.task.AbstractDataTask;
import com.photowey.juc.in.action.disruptor.task.factory.TaskFactory;

import java.util.concurrent.ExecutorService;

/**
 * {@code TaskHandler}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
public class TaskHandler<T> implements WorkHandler<DataEvent<T>> {

    private final ExecutorService executor;

    private final TaskFactory<T> taskFactory;

    public TaskHandler(final ExecutorService executor, final TaskFactory<T> taskFactory) {
        this.executor = executor;
        this.taskFactory = taskFactory;
    }

    @Override
    public void onEvent(final DataEvent<T> event) {
        if (event != null) {
            AbstractDataTask<T> dataTask = taskFactory.create();
            dataTask.setData(event.getData());
            executor.execute(dataTask);
        }
    }
}
