package com.photowey.zookeeper.in.action;

import com.photowey.zookeeper.in.action.engine.IZookeeperEngine;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

/**
 * {@code AbstractZookeeperTest}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
public abstract class AbstractZookeeperTest {

    @Autowired
    protected IZookeeperEngine zookeeperEngine;

    protected byte[] serialize(String source) {
        return source.getBytes(StandardCharsets.UTF_8);
    }

    protected String deserialize(byte[] source) {
        return new String(source, StandardCharsets.UTF_8);
    }
}
