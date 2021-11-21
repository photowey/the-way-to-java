/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */
package com.photowey.hashmap.in.action.cmap;

import jdk.internal.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@code ThreadLocalRandomExt}
 * 为了解决包可见性问题而生.
 *
 * @author photowey
 * @date 2021/11/21
 * @see {@link java.util.concurrent.ThreadLocalRandom}
 * @since 1.0.0
 */
public class ThreadLocalRandomExt {

    private static final int PROBE_INCREMENT = 0x9e3779b9;
    private static final long SEEDER_INCREMENT = 0xbb67ae8584caa73bL;

    // Unsafe mechanics
    private static final Unsafe U = Unsafe.getUnsafe();
    private static final long SEED = U.objectFieldOffset(Thread.class, "threadLocalRandomSeed");
    private static final long PROBE = U.objectFieldOffset(Thread.class, "threadLocalRandomProbe");

    static final int getProbe() {
        return U.getInt(Thread.currentThread(), PROBE);
    }

    static final void localInit() {
        int p = probeGenerator.addAndGet(PROBE_INCREMENT);
        int probe = (p == 0) ? 1 : p; // skip 0
        long seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
        Thread t = Thread.currentThread();
        U.putLong(t, SEED, seed);
        U.putInt(t, PROBE, probe);
    }

    static final int advanceProbe(int probe) {
        probe ^= probe << 13;   // xorshift
        probe ^= probe >>> 17;
        probe ^= probe << 5;
        U.putInt(Thread.currentThread(), PROBE, probe);
        return probe;
    }

    private static long mix64(long z) {
        z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL;
        z = (z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L;
        return z ^ (z >>> 33);
    }

    private static final AtomicInteger probeGenerator = new AtomicInteger();

    private static final AtomicLong seeder
            = new AtomicLong(mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime()));
}
