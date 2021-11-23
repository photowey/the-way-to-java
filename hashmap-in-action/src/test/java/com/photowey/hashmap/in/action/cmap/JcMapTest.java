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
package com.photowey.hashmap.in.action.cmap;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.raistlic.common.permutation.Permutation;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code JcMapTest}
 *
 * @author photowey
 * @date 2021/11/21
 * @since 1.0.0
 */
@Slf4j
class JcMapTest {

    /**
     * --add-opens java.base/jdk.internal.misc=ALL-UNNAMED
     */

    /**
     * Test {@link JcMap#put(java.lang.Object, java.lang.Object)}
     */
    @Test
    void testPut() {
        JcMap<String, String> map = new JcMap<>();
        map.put("key", "value");
    }

    /**
     * Test {@link JcMap#get(java.lang.Object)}
     */
    @Test
    void testGet() {
        String key = "key";
        String value = "value";
        JcMap<String, String> map = new JcMap<>();
        map.put(key, value);

        String mapValue = map.get(key);
        Assertions.assertEquals(value, mapValue);
    }

    /**
     * Test
     * {@link ConcurrentHashMap#put(java.lang.Object, java.lang.Object)}
     * {@link ConcurrentHashMap#get(java.lang.Object)}
     */
    @Test
    void testConcurrentHashMap() {
        ConcurrentHashMap map = new ConcurrentHashMap<>(32);
        String key = "key";
        String value = "value";

        map.put(key, value);

        Object mapValue = map.get(key);
        Assertions.assertEquals(value, mapValue);
    }

    /* ---------------- Print Hash -------------- */

    @Test
    void testPrintHash() {
        log.info("The Character:A hashCode is:{}", "A".hashCode());
        Integer anInt = Integer.parseInt("65");
        log.info("The Number:65 hashCode is:{}", anInt.hashCode());

        // The Character:A hashCode is:65
        // The Number:65 hashCode is:65
    }

    /* ---------------- String Hash -------------- */

    @Test
    void testStringHashCode() {
        String source = "Hello";
        // StringLatin1.hashCode(value)
        log.info("The String:source hashCode is:{}", source.hashCode());

        // The String:source hashCode is:69609650
    }

    /* ---------------- Guess Hash -------------- */

    @Test
    void testGuessStringHashCodeCollide() {
        String xy = "xy";
        // StringLatin1.hashCode(value)
        log.info("The String:xy hashCode is:{}", xy.hashCode());

        // The String:xy hashCode is:3841

        log.info("The String:A hashCode is:{}", "A".hashCode());
        log.info("The String:a hashCode is:{}", "a".hashCode());
        log.info("The String:B hashCode is:{}", "B".hashCode());
        log.info("The String:b hashCode is:{}", "b".hashCode());

        // The String:A hashCode is:65
        // The String:a hashCode is:97
        // The String:B hashCode is:66
        // The String:b hashCode is:98

        // 31 * h + (v & 0xff)
        // for i == 0
        // int h = 31 * 0 + x & 0xff == x;
        // for i == 1
        // int h = 31 * x + (y & 0xff) == 31*x+y

        // 对于长度为2的任意字符串的 {@code hashCode} 计算的表达式
        // xy
        // -> int hxy = 31*x+y
        // uv
        // -> int huv = 31*u+v

        // Hash 碰撞
        // -> hxy == huv
        // -> 31*x+y == 31*u+v
        // --> 也就是说: 满足上述这个表达式时: 字符串:xy 和 字符串:uv 就会出现 Hash 碰撞

        // 重点 - 表达式变换
        // 31*x+y - (31*u+v) == 0
        // 31*x+y-31*u-v == 0
        // 合并
        // 31(x-u)+(y-v) == 0
        // 31(x-u) == v-y

        // 令: x-u == 1
        // 则: v-y = 31
        // 也就是说: 构造长度为2任意两个字符串,只要第一位的ASCII相差1(x-u==1),并且第二位ASCII相差31(v-y==31) 那么他们就会出现 Hash 碰撞

        // The String:A hashCode is:65
        // The String:a hashCode is:97
        // The String:B hashCode is:66
        // The String:b hashCode is:98
        // 对付常用的字符: A,B,C,...,Z 来说 毫无疑问: A-B 之前 ASCII 相差1
        // 令: x == B, u == A
        // By == Av
        // 令: y == B (ASCII: 66)
        // 则: v == 31 + 66 == 97 == a
        // ->
        // BB 和 Aa 会出现 Hash 碰撞

        // 验证
        log.info("The String:BB hashCode is:{}", "BB".hashCode());
        log.info("The String:Aa hashCode is:{}", "Aa".hashCode());

        // The String:BB hashCode is:2112
        // The String:Aa hashCode is:2112

        // 自此: 我们找到了--制造出现 Hash 碰撞的钥匙
        // CollidePair: BB Aa
        // CollidePair: CC Bb

        // 验证
        log.info("The String:CC hashCode is:{}", "CC".hashCode());
        log.info("The String:Bb hashCode is:{}", "Bb".hashCode());

        // The String:CC hashCode is:2144
        // The String:Bb hashCode is:2144
    }

    @Test
    void testGuessMultiPairStringHashCodeCollide() {
        /** {@link com.photowey.hashmap.in.action.cmap.JcMapTest#testGuessStringHashCodeCollide}*/
        // --> 如果: 我们可以将 BB 和 Aa 这样的视为:碰撞对
        // --> 那么: 多组碰撞对,排列组合后.是否还会出现 Hash 碰撞呢?

        Map<String, String> dict = new HashMap<>();
        dict.put("BB", "Aa");
        dict.put("Aa", "BB");
        dict.put("CC", "Bb");
        dict.put("Bb", "CC");

        List<String> seed = Lists.newArrayList("BB", "Aa", "CC");

        Iterator<List<String>> iterator = Permutation.of(seed).iterator();
        Map<String, Object> pairs = new HashMap<>();
        iterator.forEachRemaining((groups) -> {
            List<String> mirrors = new ArrayList<>(3);
            for (String item : groups) {
                mirrors.add(dict.get(item));
            }
            pairs.put(String.join("", groups), String.join("", mirrors));
        });

        pairs.forEach((k, v) -> {
            log.info("the collide pair is:{}-{}", k, v);
        });

        // the collide pair is:BBAaCC-AaBBBb
        // the collide pair is:AaBBCC-BBAaBb
        // the collide pair is:BBCCAa-AaBbBB
        // the collide pair is:AaCCBB-BBBbAa
        // the collide pair is:CCBBAa-BbAaBB
        // the collide pair is:CCAaBB-BbBBAa

        // 64577
        log.info("ABB".hashCode() + "---" + "AAa".hashCode());
    }

    /**
     * {@link JcMapTest#testGuessMultiPairStringHashCodeCollide()}
     */
    @Test
    void testPrintMultiPairStringHashCodeCollide() {
        // 1952508128
        log.info("The String:BBAaCC-AaBBBb hashCode is:{}-{}", "BBAaCC".hashCode(), "AaBBBb".hashCode());
        log.info("The String:AaBBCC-BBAaBb hashCode is:{}-{}", "AaBBCC".hashCode(), "BBAaBb".hashCode());

        // 1952538848
        log.info("The String:BBCCAa-AaBbBB hashCode is:{}-{}", "BBCCAa".hashCode(), "AaBbBB".hashCode());
        log.info("The String:AaCCBB-BBBbAa hashCode is:{}-{}", "AaCCBB".hashCode(), "BBBbAa".hashCode());

        // 1982060768
        log.info("The String:CCBBAa-BbAaBB hashCode is:{}-{}", "CCBBAa".hashCode(), "BbAaBB".hashCode());
        log.info("The String:CCAaBB-BbBBAa hashCode is:{}-{}", "CCAaBB".hashCode(), "BbBBAa".hashCode());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CollidePair<T> implements Serializable {
        private static final long serialVersionUID = -6977032029322407845L;
        private T you;
        private T me;
    }

    /**
     * The Class String hashCode() method.
     * <pre>
     *    public int hashCode() {
     *         int h = hash;
     *         if (h == 0 && value.length > 0) {
     *             hash = h = isLatin1()
     *                     ? StringLatin1.hashCode(value)
     *                     : StringUTF16.hashCode(value);
     *         }
     *         return h;
     *     }
     * </pre>
     */

    /**
     * {@link StringLatin1#hashCode(byte[])}
     * <pre>
     *     public static int hashCode(byte[] value) {
     *         int h = 0;
     *         for (byte v : value) {
     *             h = 31 * h + (v & 0xff);
     *         }
     *         return h;
     *     }
     * </pre>
     */

    /**
     * {@link StringUTF16#hashCode(byte[])}
     * <pre>
     *     public static int hashCode(byte[] value) {
     *         int h = 0;
     *         int length = value.length >> 1;
     *         for (int i = 0; i < length; i++) {
     *             h = 31 * h + getChar(value, i);
     *         }
     *         return h;
     *     }
     * </pre>
     */

}