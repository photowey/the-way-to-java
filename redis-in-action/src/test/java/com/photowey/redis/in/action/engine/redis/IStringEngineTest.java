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
    private IRedisEngine redisEngine;

    @Test
    @SneakyThrows
    void testSet1() {
        String key = "persistence:key";
        String value = "persistence-value";
        this.redisEngine.stringEngine().set(key, value);
        String db = this.redisEngine.stringEngine().get(key);

        Assertions.assertEquals(value, db);

        Thread.sleep(6 * 1000);

        String dbNotNull = this.redisEngine.stringEngine().get(key);
        Assertions.assertNotNull(dbNotNull);
    }

    @Test
    @SneakyThrows
    void testSetTimeout() {
        String key = "persistence:timeout:key";
        String value = "persistence-timeout-value";
        this.redisEngine.stringEngine().set(key, value, 5000);

        String db = this.redisEngine.stringEngine().get(key);
        Assertions.assertEquals(value, db);

        Thread.sleep(6 * 1000);

        String dbNull = this.redisEngine.stringEngine().get(key);
        Assertions.assertNull(dbNull);
    }

    @Test
    @SneakyThrows
    void testSetTimeoutWithTimeUnit() {
        String key = "persistence:timeout:timeunit:key";
        String value = "persistence-timeout-timeunit-value";
        this.redisEngine.stringEngine().set(key, value, 5, TimeUnit.SECONDS);

        String db = this.redisEngine.stringEngine().get(key);
        Assertions.assertEquals(value, db);

        Thread.sleep(6 * 1000);

        String dbNull = this.redisEngine.stringEngine().get(key);
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

        this.redisEngine.stringEngine().mset(context);
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
        Map<String, String> context = this.redisEngine.stringEngine().mget(keys);
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
        Map<String, String> context = this.redisEngine.stringEngine().mget(keys);
        String prefixValue = "multi:value:";
        for (int i = 0; i < times - 1; i++) {
            int index = (i + 1);
            String currentKey = prefixKey + index;
            Assertions.assertEquals(prefixValue + index, context.get(currentKey));
        }

        Assertions.assertNull(context.get(prefixKey + 11));
    }


    @Test
    void testSetRange() {
        String key = "order:user:string:9527";
        String value = "photowey";
        String reset = "shark";

        this.redisEngine.stringEngine().delete(key);
        this.redisEngine.stringEngine().set(key, value);
        this.redisEngine.stringEngine().setRange(key, reset, 5L);

        String dbValue = this.redisEngine.stringEngine().get(key);

        Assertions.assertEquals("photoshark", dbValue);
    }

    @Test
    void testGetAndSet() {
        String key = "order:user:string:9527";
        String value = "photowey";
        String reset = "shark";

        this.redisEngine.stringEngine().delete(key);
        String andSet = this.redisEngine.stringEngine().getAndSet(key, value);
        String dbValue = this.redisEngine.stringEngine().getAndSet(key, reset);

        Assertions.assertNull(andSet);
        Assertions.assertEquals("photowey", dbValue);

    }

    @Test
    void testGetRange() {
        String key = "order:user:string:9527";
        String value = "photowey";
        this.redisEngine.stringEngine().delete(key);
        this.redisEngine.stringEngine().set(key, value);

        String dbValue = this.redisEngine.stringEngine().getRange(key, 5, -1L);

        Assertions.assertEquals("wey", dbValue);
    }

    @Test
    void testIncr() {
        String key = "order:user:string:9527";
        this.redisEngine.stringEngine().delete(key);

        Long incr = this.redisEngine.stringEngine().incr(key);
        Long incr2 = this.redisEngine.stringEngine().incr(key);

        Assertions.assertEquals(1L, incr);
        Assertions.assertEquals(2L, incr2);
    }

    @Test
    void testIncrBy() {
        String key = "order:user:string:9527";
        this.redisEngine.stringEngine().delete(key);

        Long incr = this.redisEngine.stringEngine().incr(key);
        Long incrBy = this.redisEngine.stringEngine().incrBy(key, 8L);

        Assertions.assertEquals(1L, incr);
        Assertions.assertEquals(9L, incrBy);
    }

    @Test
    void testDecr() {
        String key = "order:user:string:9527";
        this.redisEngine.stringEngine().delete(key);

        Long incr = this.redisEngine.stringEngine().incr(key);
        Long incrBy = this.redisEngine.stringEngine().incrBy(key, 8L);
        Long decr = this.redisEngine.stringEngine().decr(key);

        Assertions.assertEquals(1L, incr);
        Assertions.assertEquals(9L, incrBy);
        Assertions.assertEquals(8L, decr);
    }

    @Test
    void testDecrBy() {
        String key = "order:user:string:9527";
        this.redisEngine.stringEngine().delete(key);

        Long incr = this.redisEngine.stringEngine().incr(key);
        Long incrBy = this.redisEngine.stringEngine().incrBy(key, 8L);
        Long decrBy = this.redisEngine.stringEngine().decrBy(key, 9L);

        Assertions.assertEquals(1L, incr);
        Assertions.assertEquals(9L, incrBy);
        Assertions.assertEquals(0L, decrBy);
    }

    @Test
    void testExists() {
        String key = "order:user:string:9527";
        String notMatchKey = "order:user:string:notMatch";
        String value = "photowey";

        this.redisEngine.stringEngine().delete(key);
        this.redisEngine.stringEngine().delete(notMatchKey);
        this.redisEngine.stringEngine().set(key, value);

        Boolean exists = this.redisEngine.stringEngine().exists(key);
        Boolean notMatch = this.redisEngine.stringEngine().exists(notMatchKey);
        Assertions.assertTrue(exists);
        Assertions.assertFalse(notMatch);
    }

    @Test
    void testAppend() {
        String key = "order:user:string:9527";
        String value = "photo";
        String appendValue = "shark";

        this.redisEngine.stringEngine().delete(key);
        this.redisEngine.stringEngine().set(key, value);

        Integer append = this.redisEngine.stringEngine().append(key, appendValue);
        String dbValue = this.redisEngine.stringEngine().get(key);
        Assertions.assertEquals(10, append);
        Assertions.assertEquals("photoshark", dbValue);
    }

    @Test
    void testStrlen() {
        String key = "order:user:string:9527";
        String value = "photoshark";

        this.redisEngine.stringEngine().delete(key);
        this.redisEngine.stringEngine().set(key, value);

        Long strlen = this.redisEngine.stringEngine().strlen(key);

        Assertions.assertEquals(10L, strlen);
    }

    @Test
    void testDelete() {
        String key = "order:user:string:9527";
        String value = "photoshark";

        this.redisEngine.stringEngine().delete(key);
        this.redisEngine.stringEngine().set(key, value);

        String dbValue = this.redisEngine.stringEngine().get(key);
        this.redisEngine.stringEngine().delete(key);
        String deleteValue = this.redisEngine.stringEngine().get(key);

        Assertions.assertEquals("photoshark", dbValue);
        Assertions.assertNull(deleteValue);
    }
}