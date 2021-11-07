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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@code IRedisEngineTest}
 *
 * @author photowey
 * @date 2021/10/26
 * @since 1.0.0
 */
@SpringBootTest
class IRedisEngineTest {

    @Autowired
    private IRedisEngine redisEngine;

    // ==================================================================== StringRedisTemplate

    @Test
    void testStringValueStringRedisTemplate() {
        ValueOperations<String, String> valueOperations = this.redisEngine.stringRedisTemplate().opsForValue();
        valueOperations.set("string:key:1", "9527");
        String value = valueOperations.get("string:key:1");
        Assertions.assertEquals("9527", value);
    }

    @Test
    void testStringValueRedisTemplate() {
        ValueOperations<String, Object> valueOperations = this.redisEngine.redisTemplate().opsForValue();
        valueOperations.set("string:key:2", "9527");
        String value = (String) valueOperations.get("string:key:2");
        Assertions.assertEquals("9527", value);
    }

    // ==================================================================== RedisTemplate

    @Test
    void testIntegerValueRedisTemplate() {
        ValueOperations<String, Object> valueOperations = this.redisEngine.redisTemplate().opsForValue();
        valueOperations.set("integer:key:9527", 9527);
        Integer value = (Integer) valueOperations.get("integer:key:9527");
        Assertions.assertEquals(9527, value);
    }

    @Test
    void testLongValueRedisTemplate() {
        ValueOperations<String, Object> valueOperations = this.redisEngine.redisTemplate().opsForValue();
        valueOperations.set("long:key:9527", 931503661279010816L);
        Long value = (Long) valueOperations.get("long:key:9527");
        Assertions.assertEquals(931503661279010816L, value);
    }

    @Test
    void testObjectValueRedisTemplate() {
        Student student = this.populateStudent();
        ValueOperations<String, Object> valueOperations = this.redisEngine.redisTemplate().opsForValue();
        valueOperations.set("student:user:9527", student);
        Student db = (Student) valueOperations.get("student:user:9527");
        Assertions.assertEquals(student.getId(), Objects.requireNonNull(db).getId());
    }

    @Test
    void testObjectListValueRedisTemplate() {
        List<Student> students = new ArrayList<>();
        Student student = this.populateStudent();
        students.add(student);

        ValueOperations<String, Object> valueOperations = this.redisEngine.redisTemplate().opsForValue();
        valueOperations.set("student:user:list:9527", students);

        List<Student> db = (List<Student>) valueOperations.get("student:user:list:9527");
        Assertions.assertTrue(Objects.requireNonNull(db).size() == 1);
    }

    // ==================================================================== Jedis

    @Test
    void testJedisGet() {
        try (Jedis jedis = this.redisEngine.jedisEngine().jedisPool().getResource()) {
            String studentList = jedis.get("student:user:list:9527");
            System.out.println(studentList);
        }
    }

    private Student populateStudent() {
        Student student = new Student();
        student.setId(931503661279019527L);
        student.setName("photowey");
        student.setAge(18);
        List<Hobby> hobbies = new ArrayList<>();
        hobbies.add(new Hobby("ping-pang-ball"));
        hobbies.add(new Hobby("badminton"));
        hobbies.add(new Hobby("football"));
        student.setHobbies(hobbies);
        return student;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Student implements Serializable {

        private static final long serialVersionUID = -4923681619471197308L;

        private Long id;
        private String name;
        private Integer age;
        private List<Hobby> hobbies;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Hobby implements Serializable {

        private static final long serialVersionUID = 4935895609864534338L;

        private String name;
    }
}