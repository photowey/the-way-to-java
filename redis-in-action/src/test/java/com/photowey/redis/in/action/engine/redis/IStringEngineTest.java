package com.photowey.redis.in.action.engine.redis;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code IStringEngineTest}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
@SpringBootTest
class IStringEngineTest {

    @Autowired
    private IStringEngine stringEngine;

    @Test
    @SneakyThrows
    void testSet1() {
        String key = "persistence:key";
        String value = "persistence-value";
        this.stringEngine.set(key, value);
        String db = this.stringEngine.get(key);

        Assertions.assertEquals(value, db);

        Thread.sleep(6 * 1000);

        String dbNotNull = this.stringEngine.get(key);
        Assertions.assertNotNull(dbNotNull);
    }

    @Test
    @SneakyThrows
    void testSetTimeout() {
        String key = "persistence:timeout:key";
        String value = "persistence-timeout-value";
        this.stringEngine.set(key, value, 5000);

        String db = this.stringEngine.get(key);
        Assertions.assertEquals(value, db);

        Thread.sleep(6 * 1000);

        String dbNull = this.stringEngine.get(key);
        Assertions.assertNull(dbNull);
    }

    @Test
    @SneakyThrows
    void testSetTimeoutWithTimeUnit() {
        String key = "persistence:timeout:timeunit:key";
        String value = "persistence-timeout-timeunit-value";
        this.stringEngine.set(key, value, 5, TimeUnit.SECONDS);

        String db = this.stringEngine.get(key);
        Assertions.assertEquals(value, db);

        Thread.sleep(6 * 1000);

        String dbNull = this.stringEngine.get(key);
        Assertions.assertNull(dbNull);
    }

    @Test
    void testMultiSet() {
        int times = 10;
        Map<String, String> context = new HashMap<>();
        String prefixKey = "multi:key:";
        String prefixValue = "multi:value:";
        for (int i = 0; i < times; i++) {
            context.put(prefixKey + (i + 1), prefixValue + (i + 1));
        }

        this.stringEngine.mset(context);
    }

    @Test
    void testMultiGet() {
        int times = 10;
        List<String> keys = new ArrayList<>();
        String prefixKey = "multi:key:";
        for (int i = 0; i < times; i++) {
            keys.add(prefixKey + (i + 1));
        }
        String prefixValue = "multi:value:";
        Map<String, String> context = this.stringEngine.mget(keys);
        context.forEach((k, v) -> {
            String index = k.replaceAll(prefixKey, "");
            Assertions.assertEquals(prefixValue + index, v);
        });
    }

    @Test
    void testMultiGetWithKeyOverflow() {
        int times = 11;
        List<String> keys = new ArrayList<>();
        String prefixKey = "multi:key:";
        for (int i = 0; i < times; i++) {
            keys.add(prefixKey + (i + 1));
        }
        Map<String, String> context = this.stringEngine.mget(keys);
        String prefixValue = "multi:value:";
        for (int i = 0; i < times - 1; i++) {
            int index = (i + 1);
            String currentKey = prefixKey + index;
            Assertions.assertEquals(prefixValue + index, context.get(currentKey));
        }

        Assertions.assertNull(context.get(prefixKey + 11));
    }
}