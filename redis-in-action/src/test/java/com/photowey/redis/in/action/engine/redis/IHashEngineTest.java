/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code IHashEngineTest}
 *
 * @author photowey
 * @date 2021/11/06
 * @since 1.0.0
 */
@SpringBootTest
class IHashEngineTest {

    @Autowired
    private IRedisEngine redisEngine;

    @Test
    void testHset() {
        String key = "order:user:hash:9527";
        String field = "name";
        String value = "photowey";

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, field, value);

        String dbValue = this.redisEngine.hashEngine().hget(key, field);

        Assertions.assertEquals(value, dbValue);

    }

    @Test
    void testHmset() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(4);
        entries.put("name", "photowey");
        entries.put("age", "18");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Map<Object, Object> dbValues = this.redisEngine.hashEngine().hgetall(key);

        Assertions.assertEquals("photowey", dbValues.get("name"));
        Assertions.assertEquals("18", dbValues.get("age"));

    }

    @Test
    void testHmset2() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(4);
        entries.put("name", "photowey");
        entries.put("age", "18");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Student student = this.redisEngine.hashEngine().hgetall(key,
                (values) -> JSON.parseObject(JSON.toJSONString(values), Student.class));

        Assertions.assertEquals("photowey", student.getName());
        Assertions.assertEquals(18, student.getAge());

    }

    @Test
    void testHget() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        String name = this.redisEngine.hashEngine().hget(key, "name");
        String age = this.redisEngine.hashEngine().hget(key, "age");
        String country = this.redisEngine.hashEngine().hget(key, "country");

        Assertions.assertEquals("photowey", name);
        Assertions.assertEquals("18", age);
        Assertions.assertEquals("zhCN", country);

    }

    @Test
    void testHmget() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        List<Object> objects = this.redisEngine.hashEngine().hmultiGet(key, Lists.newArrayList("name", "country"));

        Assertions.assertEquals(2, objects.size());
        Assertions.assertTrue(objects.contains("photowey"));
        Assertions.assertTrue(objects.contains("zhCN"));

    }


    @Test
    void testHdel() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);
        this.redisEngine.hashEngine().hdel(key, "country");

        List<Object> objects = this.redisEngine.hashEngine().hmultiGet(key, Lists.newArrayList("name", "country"));

        Assertions.assertEquals(2, objects.size());
        Assertions.assertTrue(objects.contains("photowey"));
        Assertions.assertFalse(objects.contains("zhCN"));
        Assertions.assertTrue(objects.contains(null));

    }

    @Test
    void testHexists() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Boolean hexists = this.redisEngine.hashEngine().hexists(key, "name");
        Boolean notMatch = this.redisEngine.hashEngine().hexists(key, "notMatch");

        Assertions.assertTrue(hexists);
        Assertions.assertFalse(notMatch);

    }

    @Test
    void testHlen() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Long hlen = this.redisEngine.hashEngine().hlen(key);

        Assertions.assertEquals(3L, hlen);

    }

    @Test
    void testHstrlen() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Long hstrlen = this.redisEngine.hashEngine().hstrlen(key, "country");

        // 为什么是 6L ?
        // 由于-本项目序序列化的原因 - redis 里面多了双引号
        // @see com.photowey.redis.in.action.config.redis.RedisConfigure.redisTemplate 采用了 GenericJackson2JsonRedisSerializer
        // 如果: 能明确我们的 Redis 不会存储带有列表属性的字段就可以采用 Jackson2JsonRedisSerializer 来避免
        // country -> "zhCN" -> 4+2=6
        Assertions.assertEquals(4L + 2L, hstrlen);

    }

    @Test
    void testHkeys() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Set<Object> hkeys = this.redisEngine.hashEngine().hkeys(key);

        Assertions.assertEquals(3, hkeys.size());
        Assertions.assertTrue(hkeys.contains("name"));
        Assertions.assertTrue(hkeys.contains("age"));
        Assertions.assertTrue(hkeys.contains("country"));

    }

    @Test
    void testHvals() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", "18");
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        List<Object> hvals = this.redisEngine.hashEngine().hvals(key);

        Assertions.assertEquals(3, hvals.size());
        Assertions.assertTrue(hvals.contains("photowey"));
        Assertions.assertTrue(hvals.contains("18"));
        Assertions.assertTrue(hvals.contains("zhCN"));

    }

    @Test
    void testHincrby() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(6);
        entries.put("name", "photowey");
        entries.put("age", 18);
        entries.put("country", "zhCN");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Long hincrby = this.redisEngine.hashEngine().hincrby(key, "age", 10L);

        Assertions.assertEquals(28L, hincrby);

    }

    @Test
    void testHgetall() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(4);
        entries.put("name", "photowey");
        // 整型
        entries.put("age", 18);

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Map<Object, Object> hgetall = this.redisEngine.hashEngine().hgetall(key);

        Assertions.assertEquals("photowey", hgetall.get("name"));
        Assertions.assertEquals(18, hgetall.get("age"));

    }

    @Test
    void testHgetall2() {
        String key = "order:user:hash:9527";
        Map<Object, Object> entries = new HashMap<>(4);
        entries.put("name", "photowey");
        // 字符串
        entries.put("age", "18");

        this.redisEngine.redisTemplate().delete(key);
        this.redisEngine.hashEngine().hset(key, entries);

        Student student = this.redisEngine.hashEngine().hgetall(key,
                (values) -> JSON.parseObject(JSON.toJSONString(values), Student.class));

        Assertions.assertEquals("photowey", student.getName());
        Assertions.assertEquals(18, student.getAge());

    }

    @Data
    public static class Student implements Serializable {

        private static final long serialVersionUID = -8720192478752724426L;

        private String name;
        private Integer age;
    }

}