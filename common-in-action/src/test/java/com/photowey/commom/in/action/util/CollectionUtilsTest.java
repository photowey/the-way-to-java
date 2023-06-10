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
package com.photowey.commom.in.action.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * {@code CollectionUtilsTest}
 *
 * @author photowey
 * @date 2023/06/10
 * @since 1.0.0
 */
class CollectionUtilsTest {

    @Test
    void testUnion_int() {
        List<Integer> c1 = CollectionUtils.asList(1, 2, 3, 4, 5);
        List<Integer> c2 = CollectionUtils.asList(3, 4, 5, 6, 7);

        List<Integer> c3 = CollectionUtils.union(c1, c2);
        Assertions.assertEquals(7, c3.size());

        for (int i = 1; i < 8; i++) {
            Assertions.assertTrue(c3.contains(i));
        }
    }

    @Test
    void testUnion_all_int() {
        List<Integer> c1 = CollectionUtils.asList(1, 2, 3, 4, 5);
        List<Integer> c2 = CollectionUtils.asList(3, 4, 5, 6, 7);

        List<Integer> c3 = CollectionUtils.unionAll(c1, c2);
        Assertions.assertEquals(10, c3.size());

        for (int i = 1; i < 8; i++) {
            Assertions.assertTrue(c3.contains(i));
        }
    }

    @Test
    void testIntersection_int() {
        List<Integer> c1 = CollectionUtils.asList(1, 2, 3, 4, 5);
        List<Integer> c2 = CollectionUtils.asList(3, 4, 5, 6, 7);

        List<Integer> c3 = CollectionUtils.intersection(c1, c2);
        Assertions.assertEquals(3, c3.size());

        for (int i = 3; i < 6; i++) {
            Assertions.assertTrue(c3.contains(i));
        }
    }

    @Test
    void testIntersection_String() {
        List<String> c1 = CollectionUtils.asList("A", "1", "B", "2", "C");
        List<String> c2 = CollectionUtils.asList("A", "3", "B", "4", "C");

        List<String> c3 = CollectionUtils.intersection(c1, c2);
        Assertions.assertEquals(3, c3.size());
    }

    @Test
    void testReduce_21_int() {
        List<Integer> c1 = CollectionUtils.asList(1, 2, 3, 4, 5);
        List<Integer> c2 = CollectionUtils.asList(3, 4, 5, 6, 7);

        List<Integer> c3 = CollectionUtils.reduce21(c1, c2);
        Assertions.assertEquals(2, c3.size());

        for (int i = 6; i < 7; i++) {
            Assertions.assertTrue(c3.contains(i));
        }
    }

    @Test
    void testReduce_12_int() {
        List<Integer> c1 = CollectionUtils.asList(1, 2, 3, 4, 5);
        List<Integer> c2 = CollectionUtils.asList(3, 4, 5, 6, 7);

        List<Integer> c3 = CollectionUtils.reduce12(c1, c2);
        Assertions.assertEquals(2, c3.size());

        for (int i = 1; i < 3; i++) {
            Assertions.assertTrue(c3.contains(i));
        }
    }

    @Test
    void testReduce_21_string() {
        List<String> c1 = CollectionUtils.asList("A", "1", "B", "2", "C");
        List<String> c2 = CollectionUtils.asList("A", "3", "B", "4", "C");

        List<String> c3 = CollectionUtils.reduce21(c1, c2);
        Assertions.assertEquals(2, c3.size());

        for (int i = 3; i < 5; i++) {
            Assertions.assertTrue(c3.contains(String.valueOf(i)));
        }
    }

    @Test
    void testReduce_12_string() {
        List<String> c1 = CollectionUtils.asList("A", "1", "B", "2", "C");
        List<String> c2 = CollectionUtils.asList("A", "3", "B", "4", "C");

        List<String> c3 = CollectionUtils.reduce12(c1, c2);
        Assertions.assertEquals(2, c3.size());

        for (int i = 1; i < 3; i++) {
            Assertions.assertTrue(c3.contains(String.valueOf(i)));
        }
    }

}