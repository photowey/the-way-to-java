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
package com.photowey.juc.in.action.sync;

/**
 * {@code SyncBytecode}
 *
 * @author photowey
 * @date 2021/11/18
 * @since 1.0.0
 */
public class SyncBytecode {

    private static Object lock = new Object();

    private static int count;
    // view with jclasslib
    /**
     *  0 getstatic #2 <com/photowey/juc/in/action/sync/SyncBytecode.lock : Ljava/lang/Object;>
     *  3 dup
     *  4 astore_1
     *  5 monitorenter
     *  6 getstatic #3 <com/photowey/juc/in/action/sync/SyncBytecode.count : I>
     *  9 iconst_1
     * 10 iadd
     * 11 putstatic #3 <com/photowey/juc/in/action/sync/SyncBytecode.count : I>
     * 14 aload_1
     * 15 monitorexit
     * 16 goto 24 (+8)
     * 19 astore_2
     * 20 aload_1
     * 21 monitorexit
     * 22 aload_2
     * 23 athrow
     * 24 return
     */

    /**
     * monitorenter
     * -- ----------------------- jdk12
     * -- ----------------------- /juc-in-action/doc/object-header.jpg
     * C++ 源码
     * /src/hotspot/share/interpreter/bytecodeinterpreter.cpp     #1775
     * bytecodeinterpreter: monitorenter and monitorexit for locking/unlocking an object
     * -- -----------------------
     * // 当前锁对象
     * oop lockee = STACK_OBJECT(-1);
     * -- -----------------------
     * // 把当前锁对象->关联到lock-record的 obj ref
     * entry->set_obj(lockee);
     * // 拿到 mark-word
     * markOop mark = lockee->mark();
     * -- -----------------------
     * // 判断 JVM 有没有禁用偏向
     * mark->has_bias_pattern()
     * --> anticipated_bias_locking_value == 0 --> 表示当前线程是偏向自己
     * -- -----------------------
     * // 当前偏向线程不是对象头里面的线程
     * // traditional lightweight locking
     * if (!success) {
     * // 首先产生一个无锁的 mark-word - 01
     * markOop displaced = lockee->mark()->set_unlocked();
     * }
     * -- -----------------------
     * ObjectSynchronizer::fast_enter(Handle obj, BasicLock* lock, bool attempt_rebias, TRAPS)
     * --> void ObjectSynchronizer::slow_enter(Handle obj, BasicLock* lock, TRAPS)
     * 膨胀
     * ->
     * ObjectSynchronizer::inflate(THREAD,    obj(), inflate_cause_monitor_enter)->enter(THREAD)
     * -->
     * void ObjectMonitor::enter(TRAPS)
     * -->
     * // 看当前持有锁的线程是否为: NULL,如果没有直接获取锁 - 非公平锁
     * void * cur = Atomic::cmpxchg(Self, &_owner, (void*)NULL);
     * if (cur == NULL) {}
     * // 重入
     * if (cur == Self) {}
     * -- -----------------------
     * lock-record
     * -- _displaced_header
     * -- obj ref
     * -- -----------------------
     * object-lock
     * -- object-header
     * -- -- mark-word
     * -- -- klass-pointer
     */

    public static void main(String[] args) {

        synchronized (lock) {
            count++;
        }
    }

}
