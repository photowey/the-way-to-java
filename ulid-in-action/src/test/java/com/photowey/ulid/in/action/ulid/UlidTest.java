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
package com.photowey.ulid.in.action.ulid;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import com.github.f4b6a3.ulid.UlidFactory;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 * {@code UlidTest}
 *
 * @author photowey
 * @date 2022/10/16
 * @since 1.0.0
 */
class UlidTest {

    @Test
    void testGetUlid() {
        for (int i = 0; i < 1_000_000; i++) {
            Ulid ulid = UlidCreator.getUlid();
            // 01gfgrfexhbv6vtvjbygf06dvx
            System.out.println(ulid.toLowerCase());
        }
    }

    @Test
    void testGetMonotonicUlid() {
        for (int i = 0; i < 1_000_000; i++) {
            Ulid ulid = UlidCreator.getMonotonicUlid();
            // 01gfgrja0zgb7ms567hcz53y3r
            System.out.println(ulid.toLowerCase());
        }
    }

    @Test
    void testUlidFactory() {
        for (int i = 0; i < 1_000_000; i++) {
            UlidFactory factory = UlidFactory.newInstance(new SecureRandom());
            Ulid ulid = factory.create();
            // 01gfgrrgzthq80sn73jsvy9f63
            System.out.println(ulid.toLowerCase());
        }
    }

    @Test
    void testUlidFactoryThreadLocalRandom() {
        for (int i = 0; i < 1_000_000; i++) {
            UlidFactory factory = UlidFactory.newInstance((length) -> {
                final byte[] bytes = new byte[length];
                ThreadLocalRandom.current().nextBytes(bytes);
                return bytes;
            });
            Ulid ulid = factory.create();
            // 01gfgrtx8970pqqvgaxrjh221m
            System.out.println(ulid.toLowerCase());
        }
    }
}
