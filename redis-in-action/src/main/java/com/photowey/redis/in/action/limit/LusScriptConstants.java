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

/**
 * {@code LusScriptConstants}
 *
 * @author photowey
 * @date 2022/07/12
 * @since 1.0.0
 */
public interface LusScriptConstants {

    String LIMIT_LUA_SCRIPT = "local counter\n" +
            "counter = redis.call('get', KEYS[1])\n" +
            "if counter and tonumber(counter) > tonumber(ARGV[1]) then\n" +
            "    return counter;\n" +
            "end\n" +
            "counter = redis.call('incr', KEYS[1])\n" +
            "if tonumber(counter) == 1 then\n" +
            "    redis.call('expire', KEYS[1], ARGV[2])\n" +
            "end\n" +
            "return counter;\n";

}
