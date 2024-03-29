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
---
--- Generated by Luanalysis
--- Created by photowey.
--- DateTime: 2022/7/12 22:27
---
--- {@link org.springframework.integration.redis.util.RedisLockRegistry.OBTAIN_LOCK_SCRIPT}

local lockClientId = redis.call('GET', KEYS[1])
if lockClientId == ARGV[1] then
    redis.call('PEXPIRE', KEYS[1], ARGV[2])
    return true
elseif not lockClientId then
    redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2])
    return true
end
return false