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
package com.photowey.event.bus.in.action.event.bus.hello;

import com.photowey.event.bus.in.action.subscribe.Subscriber;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code HelloSubscriber}
 *
 * @author photowey
 * @date 2021/11/14
 * @since 1.0.0
 */
@Slf4j
public class HelloSubscriber implements Subscriber<HelloEvent> {

    @Override
    public void handleEvent(HelloEvent event) {
        log.info("process the HelloEvent in {} Subscriber,the world is:{}", this.getClass().getSimpleName(), event.getWords());
    }
}
