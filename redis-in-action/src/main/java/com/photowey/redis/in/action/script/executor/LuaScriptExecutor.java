/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.redis.in.action.script.executor;

import com.photowey.redis.in.action.proxy.template.RedisTemplateProxy;
import com.photowey.redis.in.action.script.constant.ScriptConstants;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * {@code LuaScriptExecutor}
 *
 * @author photowey
 * @date 2024/01/19
 * @since 1.0.0
 */
public class LuaScriptExecutor {

    private final RedisTemplateProxy proxy;

    private final RedisScript<Double> redisScriptLongDouble = new DefaultRedisScript<>(ScriptConstants.ZSET_SCORE_LUA_SCRIPT, Double.class);
    private final RedisScript<Integer> redisScriptInteger = new DefaultRedisScript<>(ScriptConstants.ZSET_SCORE_LUA_SCRIPT, Integer.class);
    private final RedisScript<Long> redisScriptLong = new DefaultRedisScript<>(ScriptConstants.ZSET_SCORE_LUA_SCRIPT, Long.class);

    public LuaScriptExecutor(RedisTemplateProxy proxy) {
        this.proxy = proxy;
    }

    // ----------------------------------------------------------------

    public <T> Double executeZsetLuaScript(String script, String key, T member, Double score) {
        return this.executeZsetLuaScript(script, key, member, score, Double.class);
    }

    public <T> Integer executeZsetLuaScript(String script, String key, T member, Integer score) {
        return this.executeZsetLuaScript(script, key, member, score, Integer.class);
    }

    public <T> Long executeZsetLuaScript(String script, String key, T member, Long score) {
        return this.executeZsetLuaScript(script, key, member, score, Long.class);
    }

    // ----------------------------------------------------------------

    public <T> Double executeLuaScript(String key, T member, Double score) {
        return this.executeLuaZsetScript(redisScriptLongDouble, key, member, score);
    }

    public <T> Integer executeLuaScript(String key, T member, Integer score) {
        return this.executeLuaZsetScript(redisScriptInteger, key, member, score);
    }

    public <T> Long executeLuaScript(String key, T member, Long score) {
        return this.executeLuaZsetScript(redisScriptLong, key, member, score);
    }

    // ----------------------------------------------------------------

    public <T, R> R executeZsetLuaScript(String script, String key, T member, R score, Class<R> clazz) {
        RedisScript<R> redisScript = new DefaultRedisScript<>(script, clazz);
        return this.executeLuaZsetScript(redisScript, key, member, score);
    }

    // ----------------------------------------------------------------

    public <T, R> R executeLuaZsetScript(RedisScript<R> redisScript, String key, T member, R score) {
        return this.proxy.redis().execute(redisScript, Collections.singletonList(key), member, score);
    }
}