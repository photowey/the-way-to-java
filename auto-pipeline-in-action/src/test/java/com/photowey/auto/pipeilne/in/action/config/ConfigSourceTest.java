package com.photowey.auto.pipeilne.in.action.config;

import com.photowey.auto.pipeilne.in.action.config.handler.MapConfigSourceHandler;
import com.photowey.auto.pipeilne.in.action.config.handler.SystemConfigSourceHandler;
import com.photowey.auto.pipeilne.in.action.config.pipeline.ConfigSourceHandler;
import com.photowey.auto.pipeilne.in.action.config.pipeline.ConfigSourcePipeline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code ConfigSourceTest}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
class ConfigSourceTest {

    @Test
    void testMap() {
        Map<String, String> mapConfig = new HashMap<>();
        mapConfig.put("hello", "world");

        ConfigSourceHandler mapConfigSourceHandler = new MapConfigSourceHandler(mapConfig);

        ConfigSource pipeline = new ConfigSourcePipeline()
                .addLast(mapConfigSourceHandler)
                .addLast(SystemConfigSourceHandler.INSTANCE);

        String value = pipeline.get("hello");
        // get value "world"
        // from mapConfig / mapConfigSourceHandler
        Assertions.assertEquals("world", value);

        String specification = pipeline.get("java.specification.version");
        // get value "1.8"
        // from system properties / SystemConfigSourceHandler
        Assertions.assertEquals("11", specification);
    }

}