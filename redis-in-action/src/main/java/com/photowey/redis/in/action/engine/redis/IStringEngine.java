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
package com.photowey.redis.in.action.engine.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code IStringEngine}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
public interface IStringEngine extends IEngine {

    // ========================================= set

    void set(String key, String value);

    void set(String key, String value, long timeout);

    void set(String key, String value, long timeout, TimeUnit timeUnit);

    void setRange(String key, String value, long offset);

    // ========================================= get

    String get(String key);

    String getAndSet(String key, String value);

    String getRange(String key, long start, long end);

    // ========================================= incr

    Long incr(String key);

    Long incrBy(String key, long delta);

    // ========================================= decr

    Long decr(String key);

    Long decrBy(String key, long delta);

    // ========================================= multi

    void mset(Map<String, String> context);

    Map<String, String> mget(List<String> keys);

    // ========================================= exists

    Boolean exists(String key);

    // ========================================= append

    Integer append(String key, String value);

    // ========================================= strlen

    Long strlen(String key);

    // ========================================= delete

    Boolean delete(String key);
}
