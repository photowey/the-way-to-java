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
package com.photowey.plugin.xcurl.formatter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

/**
 * {@code Formatter}
 *
 * @author photowey
 * @date 2022/11/16
 * @since 1.0.0
 */
public final class Formatter {

    private Formatter() {
        // util class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String parseObject(String content) {


        JSONObject body = JSON.parseObject(content, JSONObject.class);
        return JSON.toJSONString(body, JSONWriter.Feature.PrettyFormat);
    }

    public static String parseArray(String content) {
        JSONArray body = JSON.parseObject(content, JSONArray.class);
        return JSON.toJSONString(body, JSONWriter.Feature.PrettyFormat);
    }

    public static String tryFormat(String content) {
        if (JSON.isValid(content)) {
            return parseObject(content);
        } else if (JSON.isValidArray(content)) {
            return parseArray(content);
        }

        return tryXml(content);
    }

    public static String tryJson(String json) {
        if (JSON.isValid(json)) {
            return parseObject(json);
        } else if (JSON.isValidArray(json)) {
            return parseArray(json);
        }

        return json;
    }

    public static String tryXml(String content) {
        return formatXml(content);
    }

    public static String formatXml(String xml) {
        return xml;
    }
}
