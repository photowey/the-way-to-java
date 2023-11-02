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
package com.photowey.common.in.action.json;

import com.google.common.collect.Lists;
import com.photowey.common.in.action.shared.json.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * {@code JsonTest}
 *
 * @author photowey
 * @date 2023/10/26
 * @since 1.0.0
 */
@Slf4j
class JsonTest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person implements Serializable {

        private static final long serialVersionUID = -7622481177614899749L;

        private Long id;
        private String name;
        private Integer age;
    }

    @Test
    void testJson() {
        Person p1 = Person.builder()
                .id(System.currentTimeMillis())
                .name("tom").age(18)
                .build();
        Person p2 = Person.builder()
                .id(System.currentTimeMillis())
                .name("tom").age(18)
                .build();

        String object = Json.write(p1, Person.class);
        log.info("the json object is:{}", object);

        Map<String, Object> map = Json.toMap(object);
        log.info("the json object.map is:{}", Json.write(map));

        List<Person> pn = Lists.newArrayList(p1, p2);
        String array = Json.write(pn, Person.class);
        log.info("the json array is:{}", array);

        Assertions.assertNotNull(array);

        List<Person> p11 = Json.toList(array);
        log.info("the json array.p11 is:{}", Json.write(p11, Person.class));
    }
}