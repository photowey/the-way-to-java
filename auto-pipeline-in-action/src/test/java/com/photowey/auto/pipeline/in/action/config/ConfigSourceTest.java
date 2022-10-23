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
package com.photowey.auto.pipeline.in.action.config;

import com.photowey.auto.pipeline.in.action.config.handler.MapConfigSourceHandler;
import com.photowey.auto.pipeline.in.action.config.handler.SystemConfigSourceHandler;
import com.photowey.auto.pipeline.in.action.config.pipeline.ConfigSourceHandler;
import com.photowey.auto.pipeline.in.action.config.pipeline.ConfigSourcePipeline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code ConfigSourceTest}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
class ConfigSourceTest {

    @Test
    void testMap() {
        Map<String, String> mapConfig = new HashMap<>();
        mapConfig.put("hello", "world");

        ConfigSourceHandler mapConfigSourceHandler = new MapConfigSourceHandler(mapConfig);

        ConfigSource pipeline = new ConfigSourcePipeline()
                .addLast(mapConfigSourceHandler)
                .addLast(SystemConfigSourceHandler.INSTANCE);

        String value = pipeline.get("hello");
        // get value "world"
        // from mapConfig / mapConfigSourceHandler
        Assertions.assertEquals("world", value);

        String specification = pipeline.get("java.specification.version");
        // get value "1.8"
        // from system properties / SystemConfigSourceHandler
        Assertions.assertEquals("11", specification);
    }

}