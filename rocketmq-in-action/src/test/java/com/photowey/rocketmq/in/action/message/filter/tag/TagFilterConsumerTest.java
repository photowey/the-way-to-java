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
package com.photowey.rocketmq.in.action.message.filter.tag;

import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code TagFilterConsumerTest}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
@SpringBootTest
class TagFilterConsumerTest {

    @Autowired
    private TagFilterConsumer tagFilterConsumer;

    @Test
    void teatTagFilterMessage() throws MQClientException, InterruptedException {
        this.tagFilterConsumer.consume();
        Thread.sleep(5_000);
    }
}