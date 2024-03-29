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
package com.photowey.juc.in.action.disruptor.task;

import com.photowey.juc.in.action.disruptor.subscriber.TaskSubscriber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code DefaultTask}
 *
 * @author photowey
 * @date 2021/12/04
 * @since 1.0.0
 */
public class DefaultTask extends AbstractDataTask<String> {

    private Set<TaskSubscriber<String>> subscribers = new HashSet<>();

    public DefaultTask(Set<TaskSubscriber<String>> subscribers) {
        this.subscribers.addAll(subscribers);
    }

    @Override
    public void run() {
        String data = this.getData();
        List<String> dataList = new ArrayList<>();
        dataList.add(data);
        subscribers.forEach(subscriber -> subscriber.execute(dataList));
        // TODO Support the TaskContext<T> in the future.
    }

//    public class TaskContext<T> {
//        private T data;
//        private long occur;
//    }
}
