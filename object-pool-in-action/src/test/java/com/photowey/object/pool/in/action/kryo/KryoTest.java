/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.object.pool.in.action.kryo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * {@code KryoTest}
 *
 * @author photowey
 * @since 2024/07/10
 */
class KryoTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testKryo() {
        KryoFactory factory = new DefaultKryoFactory();
        KryoPool pool = new KryoPool.Builder(factory).build();

        Student student = Student.builder()
                .id(0L)
                .name("photowey")
                .age(18)
                .build();

        Input input = pool.run((kryo) -> {
            return kryo.encode(student);
        });

        Output output = pool.run((kryo) -> {
            return kryo.decode(input);
        });

        Student copy = this.parseObject(output, Student.class);

        Assertions.assertEquals(copy.getId(), student.getId());
        Assertions.assertEquals(copy.getName(), student.getName());
        Assertions.assertEquals(copy.getAge(), student.getAge());

        ((KryoPoolQueueImpl) pool).clear();
    }

    private <T> T parseObject(Input input, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(input.getData(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T parseObject(Output output, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(output.getData(), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void configureKryo(Kryo kryo) {

    }

    static class DefaultKryoFactory implements KryoFactory {

        @Override
        public Kryo create() {
            Kryo kryo = new Kryo(new ObjectMapper());
            configureKryo(kryo);

            return kryo;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Student implements Serializable {

        private static final long serialVersionUID = 8458084410169159367L;

        private Long id;
        private String name;
        private Integer age;
    }

}