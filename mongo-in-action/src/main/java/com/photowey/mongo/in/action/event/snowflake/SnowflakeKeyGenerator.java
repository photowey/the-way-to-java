package com.photowey.mongo.in.action.event.snowflake;

import com.photowey.mongo.in.action.event.generator.MongoKeyGenerator;
import com.photowey.mongo.in.action.event.nanoid.NanoidUtils;
import com.photowey.mongo.in.action.event.shared.Sequence;

/**
 * {@code SnowflakeKeyGenerator}
 *
 * @author photowey
 * @date 2022/10/27
 * @since 1.0.0
 */
public class SnowflakeKeyGenerator implements MongoKeyGenerator {

    private final Sequence sequence;

    public SnowflakeKeyGenerator() {
        this.sequence = new Sequence();
    }

    @Override
    public long nextId() {
        return this.sequence.nextId();
    }

    @Override
    public String objectId() {
        return String.valueOf(this.nextId());
    }

    @Override
    public String nanoId() {
        return NanoidUtils.randomNanoId();
    }
}
