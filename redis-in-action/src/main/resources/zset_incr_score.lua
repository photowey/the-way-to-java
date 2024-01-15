local score = redis.call('zscore', KEYS[1], ARGV[1]);

if score then
    local incremented = redis.call('zincrby', KEYS[1], ARGV[2], ARGV[1]);
    if not incremented then
        return 0;
    end
    return tonumber(incremented);
else
    local added = redis.call('zadd', KEYS[1], ARGV[2], ARGV[1]);
    if not added then
        return 0;
    end
    local newScore = redis.call('zscore', KEYS[1], ARGV[1]);
    if not newScore then
        return 0;
    end
    return tonumber(newScore);
end