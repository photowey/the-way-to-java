--[[

    Copyright © 2021 the original author or authors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

]]
--- 读锁定时,锁状态置为1,不做任何阻塞
--- 写锁定时,锁状态置为2,阻塞其他读写
--- 无锁时,锁状态为0,或者不存在该 key

--- 返回 3表示需要阻塞
--- 返回 2表示可执行

local stat = redis.call('GET', KEYS[1]);

-- 无锁时,返回可执行,并标记为写锁中
if not stat then
    redis.call('SETEX', KEYS[1], ARGV[1], 2)
    return 2;
end

-- 无锁,返回可执行,标记为写锁中
if math.abs(tonumber(stat)) < 0.1 then
    redis.call('SETEX', KEYS[1], ARGV[1], 2)
    return 2;
end

-- 写锁定时,返回阻塞
if math.abs(tonumber(stat) - 2) < 0.1 then
    return 3;
end

-- 读锁定时,返回阻塞
if math.abs(tonumber(stat) - 1) < 0.1 then
    return 3;
end

-- 预期之外的结果
return 4;