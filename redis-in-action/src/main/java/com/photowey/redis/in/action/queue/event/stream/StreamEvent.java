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
package com.photowey.redis.in.action.queue.event.stream;

import com.photowey.redis.in.action.queue.event.IRedisEvent;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * {@code StreamEvent}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
public class StreamEvent extends ApplicationEvent implements IRedisEvent {

    public StreamEvent(Map<String, String> source) {
        super(source);
    }
}
