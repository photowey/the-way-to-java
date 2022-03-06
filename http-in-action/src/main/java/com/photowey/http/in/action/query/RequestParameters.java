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
package com.photowey.http.in.action.query;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code RequestParameters}
 * 请求参数
 *
 * @author weichangjun
 * @date 2022/02/28
 * @since 1.0.0
 */
public class RequestParameters {

    private static final ConcurrentHashMap<String, Object> PARAMS_CONTEXT = new ConcurrentHashMap<>();

    public <T> RequestParameters add(String key, T value) {
        PARAMS_CONTEXT.put(key, value);

        return this;
    }

    public <T> RequestParameters add(Map<String, Object> params) {
        PARAMS_CONTEXT.putAll(params);

        return this;
    }

    public <T> T get(String key) {
        return (T) PARAMS_CONTEXT.get(key);
    }

    public <T> Map<String, Object> get() {
        return PARAMS_CONTEXT;
    }

}
