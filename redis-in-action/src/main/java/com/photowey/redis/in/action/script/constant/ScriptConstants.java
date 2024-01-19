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
package com.photowey.redis.in.action.script.constant;

/**
 * {@code ScriptConstants}
 *
 * @author photowey
 * @date 2024/01/19
 * @since 1.0.0
 */
public interface ScriptConstants {

    String ZSET_SCORE_LUA_SCRIPT = "local score = redis.call('zscore', KEYS[1], ARGV[1]);\n" +
            "\n" +
            "if score then\n" +
            "    local incremented = redis.call('zincrby', KEYS[1], ARGV[2], ARGV[1]);\n" +
            "    if not incremented then\n" +
            "        return 0;\n" +
            "    end\n" +
            "    return tonumber(incremented); \n" +
            "else\n" +
            "    local added = redis.call('zadd', KEYS[1], ARGV[2], ARGV[1]);\n" +
            "    if not added then\n" +
            "        return 0;\n" +
            "    end\n" +
            "    local newScore = redis.call('zscore', KEYS[1], ARGV[1]);\n" +
            "    if not newScore then\n" +
            "        return 0;\n" +
            "    end\n" +
            "    return tonumber(newScore);\n" +
            "end";
}
