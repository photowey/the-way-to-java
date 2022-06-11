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
package com.photowey.zookeeper.in.action;

import com.photowey.zookeeper.in.action.engine.IZookeeperEngine;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

/**
 * {@code AbstractZookeeperTest}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
public abstract class AbstractZookeeperTest {

    @Autowired
    protected IZookeeperEngine zookeeperEngine;

    protected byte[] serialize(String source) {
        return source.getBytes(StandardCharsets.UTF_8);
    }

    protected String deserialize(byte[] source) {
        return new String(source, StandardCharsets.UTF_8);
    }
}
