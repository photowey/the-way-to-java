--- 读锁定时,锁状态置为1,不做任何阻塞
--- 写锁定时, 锁状态置为2, 阻塞其他读写
--- 无锁时,锁状态为0,或者不存在该 key

--- 返回2表示可执行
--- 返回3表示需要阻塞

local stat = redis.call('GET', KEYS[1]);

-- 不存在,无锁时,返回可执行,并标记为读锁中
if not stat then
    redis.call('SETEX', KEYS[1], ARGV[1], 1)
    return 2;
end

-- 存在,但是出于无锁状态,返回可执行,标记为读锁中
if tonumber(stat) == 0 then
    redis.call('SETEX', KEYS[1], ARGV[1], 1)
    return 2;
end

-- 写锁定时,返回阻塞
if tonumber(stat) == 2 then
    return 3;
end

-- 读锁定时,返回放行
if tonumber(stat) == 1 then
    return 2;
end

-- 预期之外的结果
return 4;