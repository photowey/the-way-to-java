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
package com.photowey.juc.in.action.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.util.ReflectionUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * {@code JucTest}
 *
 * @author photowey
 * @date 2021/11/17
 * @since 1.0.0
 */
@Slf4j
class JucTest {

    /**
     * <pre>
     * object header
     * Common structure at the beginning of every GC-managed heap object.
     * (Every oop points to an object header.) Includes fundamental information about the heap object's layout,
     * type, GC state, synchronization state, and identity hash code. Consists of two words.
     * In arrays it is immediately followed by a length field.
     * Note that both Java objects and VM-internal objects have a common object header format.
     * </pre>
     *
     * <pre>
     * type:
     * GC state:
     * synchronization state:
     * identity hash code:
     * </pre>
     *
     * @see * http://openjdk.java.net/groups/hotspot/docs/HotSpotGlossary.html
     */

    /**
     * object header == mark-word(84bit=8byte) + klass-pointer(32bit=4byte)
     *
     * <pre>
     * mark word
     * The first word of every object header.
     * Usually a set of bitfields including synchronization state and identity hash code.
     * May also be a pointer (with characteristic low bit encoding) to synchronization related information.
     * During GC, may contain GC state bits.
     * </pre>
     *
     * <pre>
     * klass pointer
     * The second word of every object header.
     * Points to another object (a metaobject) which describes the layout and behavior of the original object.
     * For Java objects, the "klass" contains a C++ style "vtable".
     * </pre>
     */

    @Test
    void testPrintClassLayout() {
        Juc juc = new Juc();
        String printable = ClassLayout.parseInstance(juc).toPrintable();
        log.info(printable);

        /**
         * # WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           0d 00 00 00 (00001101 00000000 00000000 00000000) (13)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           74 7f 02 20 (01110100 01111111 00000010 00100000) (537034612)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
    }

    @Test
    void testPrintClassLayoutWithHashcode() {
        Juc juc = new Juc();
        int hashCode = juc.hashCode();
        // 6ed3ccb2
        log.info(Integer.toHexString(hashCode));
        String printable = ClassLayout.parseInstance(juc).toPrintable();
        log.info(printable);

        /**
         * 6ed3ccb2
         * -- ----------------------------
         * 6e-d3-cc-b2
         * -- ----------------------------
         * 01-b2-cc-d3-6e-00-00-00
         * -- ----------------------------
         * -> 小端存储
         * - 高字节-存在高地址
         * - 低字节-存在低地址
         * -- ----------------------------
         * # WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           01 b2 cc d3 (00000001 10110010 11001100 11010011) (-741559807)
         *       4     4        (object header)                           6e 00 00 00 (01101110 00000000 00000000 00000000) (110)
         *       8     4        (object header)                           74 7f 02 20 (01110100 01111111 00000010 00100000) (537034612)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
    }

    @Test
    void testPrintClassLayoutInt() {
        JucInt jucInt = new JucInt();
        String printable = ClassLayout.parseInstance(jucInt).toPrintable();
        log.info(printable);

        /**
         * # WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
         * com.photowey.juc.in.action.domain.JucInt object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           0d 00 00 00 (00001101 00000000 00000000 00000000) (13)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           74 7f 02 20 (01110100 01111111 00000010 00100000) (537034612)
         *      12     4    int JucInt.pointer                            0
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */

    }

    @Test
    void testCalculateHashCode() {
        Juc juc = new Juc();
        int hashCode = juc.hashCode();
        log.info(Integer.toHexString(hashCode));
        log.info(calculateHashCode(juc));
        String printable = ClassLayout.parseInstance(juc).toPrintable();
        log.info(printable);

        // 25 + 31 + 1 + 4 + 1 + 2
        // 00001001 10110010 11001100 11010011 01101110 00000000 00000000 00000000
        // ->
        // 00001001
        // 0 0001 0 01 (1-4-1-2)
        // 最后两位:
        // 01: 偏向锁
        // 00: 轻量锁
        // 10: 重量锁
        // 11: GC
        // 如果对象加上了 hashcode -> 该对象就不能进行偏向

        // 未偏向
        // 1.无锁不可偏向-有hash
        // 2.无锁可偏向-无hash
        // 3.无锁不可偏向-无hash
    }

    @Test
    void testBiased() {
        Juc juc = new Juc();
        // 无锁可偏向 - 但是未偏向
        // 101
        String printable = ClassLayout.parseInstance(juc).toPrintable();
        log.info(printable);
        /**
         * # WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           0d 00 00 00 (00001101 00000000 00000000 00000000) (13)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           74 7f 02 20 (01110100 01111111 00000010 00100000) (537034612)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        synchronized (juc) {
            // 有锁 - 偏向锁
            String printableBiased = ClassLayout.parseInstance(juc).toPrintable();
            log.info(printableBiased);
            /**
             * com.photowey.juc.in.action.domain.Juc object internals:
             *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
             *       0     4        (object header)                           0d 08 5e 76 (00001101 00001000 01011110 01110110) (1985873933)
             *       4     4        (object header)                           dc 01 00 00 (11011100 00000001 00000000 00000000) (476)
             *       8     4        (object header)                           74 7f 02 20 (01110100 01111111 00000010 00100000) (537034612)
             *      12     4        (loss due to the next object alignment)
             * Instance size: 16 bytes
             * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
             */
        }

    }

    @Test
    void testBiasedHashCode() {
        Juc juc = new Juc();
        // 0 01
        int hashCode = juc.hashCode();
        log.info(Integer.toHexString(hashCode));
        String printable = ClassLayout.parseInstance(juc).toPrintable();
        log.info(printable);
        /**
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           09 b2 cc d3 (00001001 10110010 11001100 11010011) (-741559799)
         *       4     4        (object header)                           6e 00 00 00 (01101110 00000000 00000000 00000000) (110)
         *       8     4        (object header)                           74 7f 02 20 (01110100 01111111 00000010 00100000) (537034612)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        synchronized (juc) {
            // 有锁 - 清凉锁
            // 0 00
            String printableBiased = ClassLayout.parseInstance(juc).toPrintable();
            log.info(printableBiased);
            /**
             * com.photowey.juc.in.action.domain.Juc object internals:
             *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
             *       0     4        (object header)                           f8 ca ef 0e (11111000 11001010 11101111 00001110) (250596088)
             *       4     4        (object header)                           d0 00 00 00 (11010000 00000000 00000000 00000000) (208)
             *       8     4        (object header)                           74 7f 02 20 (01110100 01111111 00000010 00100000) (537034612)
             *      12     4        (loss due to the next object alignment)
             * Instance size: 16 bytes
             * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
             */
        }

        /**
         * 前提:
         * 1.没有计算 hashcode
         * 2.偏向锁打开
         * -- -------------------------
         * 1.当一把锁第一次被线程持有的时候 - 偏向锁
         * 2.如果这个线程再次加锁 - 还是-偏向锁
         * 3.如果别的线程来加锁:
         * 3.1.交替执行: 轻量锁
         * 3.2.资源竞争: 重量锁
         */
    }

    // ================================================

    @Test
    void testLock() {
        // 计算 hashcode -> 变为轻量锁
        int hashCode = lock.hashCode();
        log.info(Integer.toHexString(hashCode));

        Thread t1 = new Thread(this::doLock, "t1");

        Thread t2 = new Thread(this::doLock, "t2");

        t1.start();
        t2.start();

        // === 资源竞争 - 重量锁

        /**
         * ------------------thread:name:t1------------------
         * # WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           02 c4 0d 7e (00000010 11000100 00001101 01111110) (2114831362)
         *       4     4        (object header)                           8b 02 00 00 (10001011 00000010 00000000 00000000) (651)
         *       8     4        (object header)                           0b 77 02 20 (00001011 01110111 00000010 00100000) (537032459)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        /**
         * ------------------thread:name:t2------------------
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           02 c4 0d 7e (00000010 11000100 00001101 01111110) (2114831362)
         *       4     4        (object header)                           8b 02 00 00 (10001011 00000010 00000000 00000000) (651)
         *       8     4        (object header)                           0b 77 02 20 (00001011 01110111 00000010 00100000) (537032459)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        try {
            Thread.sleep(10_000);
        } catch (Exception e) {
        }

    }

    @Test
    void testLockJoin() throws InterruptedException {
        // 计算 hashcode -> 变为轻量锁
        int hashCode = lock.hashCode();
        log.info(Integer.toHexString(hashCode));

        Thread t1 = new Thread(this::doLock, "t1");

        Thread t2 = new Thread(this::doLock, "t2");

        // 交替执行 - 轻量锁

        /**
         * ------------------thread:name:t1------------------
         * # WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           b0 ed 0f b2 (10110000 11101101 00001111 10110010) (-1307578960)
         *       4     4        (object header)                           97 00 00 00 (10010111 00000000 00000000 00000000) (151)
         *       8     4        (object header)                           0b 77 02 20 (00001011 01110111 00000010 00100000) (537032459)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        /**
         * ------------------thread:name:t2------------------
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           f0 f0 9f b1 (11110000 11110000 10011111 10110001) (-1314918160)
         *       4     4        (object header)                           97 00 00 00 (10010111 00000000 00000000 00000000) (151)
         *       8     4        (object header)                           0b 77 02 20 (00001011 01110111 00000010 00100000) (537032459)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        t1.start();
        t1.join();
        t2.start();
        t2.join();

        try {
            Thread.sleep(10_000);
        } catch (Exception e) {
        }

    }

    @Test
    void testLockJoinWithoutHashCode() throws InterruptedException {

        log.info("------------------未开启线程------------------");
        String printableBiased = ClassLayout.parseInstance(lock).toPrintable();
        log.info(printableBiased);

        /**
         * -- --------------------
         * 101
         * -- --------------------
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           0d 00 00 00 (00001101 00000000 00000000 00000000) (13)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           0b 77 02 20 (00001011 01110111 00000010 00100000) (537032459)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        Thread t1 = new Thread(this::doLock, "t1");

        Thread t2 = new Thread(this::doLock, "t2");

        // 交替执行 - 轻量锁

        /**
         * -- --------------------
         * 101
         * -- --------------------
         * ------------------thread:name:t1------------------
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           0d 78 30 57 (00001101 01111000 00110000 01010111) (1462794253)
         *       4     4        (object header)                           59 02 00 00 (01011001 00000010 00000000 00000000) (601)
         *       8     4        (object header)                           0b 77 02 20 (00001011 01110111 00000010 00100000) (537032459)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        /**
         * -- --------------------
         * 000
         * -- --------------------
         * ------------------thread:name:t2------------------
         * com.photowey.juc.in.action.domain.Juc object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           70 ee 9f 94 (01110000 11101110 10011111 10010100) (-1801458064)
         *       4     4        (object header)                           67 00 00 00 (01100111 00000000 00000000 00000000) (103)
         *       8     4        (object header)                           0b 77 02 20 (00001011 01110111 00000010 00100000) (537032459)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        t1.start();
        t1.join();
        t2.start();
        t2.join();

        try {
            Thread.sleep(10_000);
        } catch (Exception e) {
        }

    }

    private Juc lock = new Juc();

    public void doLock() {
        // 偏向锁 - 首先判断-是否可偏向 - 判断是否偏向了 - 拿到线程id - 通过 CAS 设置对象头
        synchronized (lock) {
            log.info("------------------thread:name:" + Thread.currentThread().getName() + "------------------");
            String printableBiased = ClassLayout.parseInstance(lock).toPrintable();
            log.info(printableBiased);
        }
    }

    // ================================================

    private static String calculateHashCode(Object object) {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            ReflectionUtils.makeAccessible(theUnsafe);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            long hashCode = 0L;
            for (int i = 7; i > 0; i--) {
                hashCode |= (unsafe.getByte(object, i) & 0xFF) << ((i - 1) * 8);
            }

            return Long.toHexString(hashCode);
        } catch (Exception e) {
            // Ignore
        }

        return "FF";
    }
}