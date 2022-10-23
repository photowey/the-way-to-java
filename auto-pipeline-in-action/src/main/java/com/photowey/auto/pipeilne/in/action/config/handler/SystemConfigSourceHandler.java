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
package com.photowey.auto.pipeilne.in.action.config.handler;

import com.photowey.auto.pipeilne.in.action.config.pipeline.ConfigSourceHandler;
import com.photowey.auto.pipeilne.in.action.config.pipeline.ConfigSourceHandlerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * {@code SystemConfigSourceHandler}
 *
 * @author photowey
 * @date 2022/10/23
 * @copy from https://github.com/photowey/auto-pipeline/blob/main/auto-pipeline-examples/src/main/java/com/foldright/examples/config/handler/SystemConfigSourceHandler.java
 * @since 1.0.0
 */
public class SystemConfigSourceHandler implements ConfigSourceHandler {

    public static final SystemConfigSourceHandler INSTANCE = new SystemConfigSourceHandler();

    @Override
    public String get(String key, ConfigSourceHandlerContext context) {
        String value = System.getProperty(key);
        if (StringUtils.isNotBlank(value)) {
            return value;
        }

        return context.get(key);
    }
}