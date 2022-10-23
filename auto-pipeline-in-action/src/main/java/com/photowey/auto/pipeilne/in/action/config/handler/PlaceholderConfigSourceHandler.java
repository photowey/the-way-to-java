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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code PlaceholderConfigSourceHandler}
 * Support value with placeholder with format {@code ${value}}.
 * <p>
 * Not support nested placeholder yet, e.g. {@code ${${value}}}.
 *
 * @author photowey
 * @date 2022/10/23
 * @copy from https://github.com/photowey/auto-pipeline/blob/main/auto-pipeline-examples/src/main/java/com/foldright/examples/config/handler/PlaceholderConfigSourceHandler.java
 * @since 1.0.0
 */
public class PlaceholderConfigSourceHandler implements ConfigSourceHandler {

    private static final Pattern PATTERN = Pattern.compile("(\\$\\{.+?})");

    @Override
    public String get(String key, ConfigSourceHandlerContext context) {
        String value = context.get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }

        Matcher matcher = PATTERN.matcher(value);

        if (!matcher.find()) {
            return value;
        }

        int groupCount = matcher.groupCount();
        for (int i = 0; i < groupCount; i++) {

            // like ${xxx}
            String matched = matcher.group(i + 1);
            // like xxx
            String placeholderKey = StringUtils.removeEnd(StringUtils.removeStart(matched, "${"), "}");
            // get xxx's value and replace ${xxx} with placeholderValue
            String placeholderValue = context.pipeline().get(placeholderKey);
            value = StringUtils.replace(value, matched, placeholderValue);
        }

        if (StringUtils.isBlank(value)) {
            return null;
        }

        return value;
    }
}