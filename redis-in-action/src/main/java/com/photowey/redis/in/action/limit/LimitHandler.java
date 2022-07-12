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
package com.photowey.redis.in.action.limit;

import io.lettuce.core.internal.LettuceLists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@code LimitHandler}
 *
 * @author photowey
 * @date 2022/07/12
 * @since 1.0.0
 */
@Component
public class LimitHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    public LimitHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long limit(String key, Integer limit, Integer period) {
        String luaScript = buildLuaScript();
        List<String> keys = LettuceLists.newList(key);
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Long counter = redisTemplate.execute(redisScript, keys, limit, period);
        if (counter == null) {
            return 0L;
        }

        return counter;
    }

    private String buildLuaScript() {
        return LusScriptConstants.LIMIT_LUA_SCRIPT;
    }

}
