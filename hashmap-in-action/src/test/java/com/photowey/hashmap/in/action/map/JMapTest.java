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
package com.photowey.hashmap.in.action.map;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.raistlic.common.permutation.Permutation;

import java.util.*;

/**
 * {@code JMapTest}
 *
 * @author photowey
 * @date 2021/11/22
 * @since 1.0.0
 */
@Slf4j
class JMapTest {

    @Test
    void testPut() {
        JMap<String, String> map = new JMap<>();
        map.put("key", "value");
    }

    /**
     * Test {@link JMap#get(java.lang.Object)}
     */
    @Test
    void testGet() {
        String key = "key";
        String value = "value";
        JMap<String, String> map = new JMap<>();
        map.put(key, value);

        String mapValue = map.get(key);
        Assertions.assertEquals(value, mapValue);
    }

    /**
     * Test
     * {@link HashMap#put(java.lang.Object, java.lang.Object)}
     * {@link HashMap#get(java.lang.Object)}
     */
    @Test
    void testHashMap() {
        HashMap map = new HashMap<>(32);
        String key = "key";
        String value = "value";

        map.put(key, value);

        Object mapValue = map.get(key);
        Assertions.assertEquals(value, mapValue);
    }

    @Test
    void testJMap() {
        // threshold
        JMap<String, String> map = new JMap<>(64);

        // 先填充56位
        for (int i = 0; i < 40; i++) {
            String source = String.valueOf((i + 1));
            map.put(source, source);
        }

        /**
         *  the collide pair is:CCBBDD-BbAaCc-->hashCode==1982060832-1982060832
         *  the collide pair is:CCBBCc-BbAaDD-->hashCode==1982060832-1982060832
         *  the collide pair is:BbBBDD-CCAaCc-->hashCode==1982060832-1982060832
         *  the collide pair is:BbBBCc-CCAaDD-->hashCode==1982060832-1982060832
         *  the collide pair is:CCAaDD-BbBBCc-->hashCode==1982060832-1982060832
         *  the collide pair is:CCAaCc-BbBBDD-->hashCode==1982060832-1982060832
         *  the collide pair is:BbAaDD-CCBBCc-->hashCode==1982060832-1982060832
         *  the collide pair is:BbAaCc-CCBBDD-->hashCode==1982060832-1982060832
         */

        //  构造 碰撞
        map.put("CCBBDD", "CCBBDD");
        map.put("BbAaCc", "BbAaCc");
        map.put("CCBBCc", "CCBBCc");
        map.put("BbAaDD", "BbAaDD");
        map.put("BbBBDD", "BbBBDD");
        map.put("CCAaCc", "CCAaCc");
        map.put("BbBBCc", "BbBBCc");

        map.put("CCAaDD", "CCAaDD");
        log.info("the thread:t1 put the K-V:{}-{} succeed", "BB", "BB");

        try {
            Thread.sleep(1000_000);
        } catch (Exception e) {
        }
    }

    @Test
    void testGuessMultiPairStringHashCodeCollide() {
        Map<String, String> dict = new HashMap<>();
        dict.put("BB", "Aa");
        dict.put("Aa", "BB");
        dict.put("CC", "Bb");
        dict.put("Bb", "CC");
        dict.put("DD", "Cc");
        dict.put("Cc", "DD");

        List<String> seed = Lists.newArrayList("BB", "Aa", "CC", "Bb", "DD", "Cc");

        Iterator<List<String>> iterator = Permutation.of(seed, 3).iterator();
        Map<String, Object> pairs = new HashMap<>();
        iterator.forEachRemaining((groups) -> {
            List<String> mirrors = new ArrayList<>(3);
            for (String item : groups) {
                mirrors.add(dict.get(item));
            }
            pairs.put(String.join("", groups), String.join("", mirrors));
        });

        pairs.forEach((k, v) -> {
            log.info("the collide pair is:{}-{}-->hashCode=={}-{}", k, v, k.hashCode(), v.hashCode());
        });

        /**
         *  the collide pair is:CCBBDD-BbAaCc-->hashCode==1982060832-1982060832
         *  the collide pair is:CCBBCc-BbAaDD-->hashCode==1982060832-1982060832
         *  the collide pair is:BbBBDD-CCAaCc-->hashCode==1982060832-1982060832
         *  the collide pair is:BbBBCc-CCAaDD-->hashCode==1982060832-1982060832
         *  the collide pair is:CCAaDD-BbBBCc-->hashCode==1982060832-1982060832
         *  the collide pair is:CCAaCc-BbBBDD-->hashCode==1982060832-1982060832
         *  the collide pair is:BbAaDD-CCBBCc-->hashCode==1982060832-1982060832
         *  the collide pair is:BbAaCc-CCBBDD-->hashCode==1982060832-1982060832
         */
    }
}