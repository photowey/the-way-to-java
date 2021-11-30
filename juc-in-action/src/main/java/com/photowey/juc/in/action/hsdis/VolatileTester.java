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
package com.photowey.juc.in.action.hsdis;

/**
 * {@code VolatileTester}
 * hsdis: HotSpot disassembler
 *
 * @author photowey
 * @date 2021/12/01
 * @since 1.0.0
 */
public class VolatileTester {
    /**
     * -server
     * -Xcomp
     * -XX:+UnlockDiagnosticVMOptions
     * -XX:+PrintAssembly
     * -XX:CompileCommand=compileonly,*VolatileTester.doVolatile()
     */
    /**
     * -server -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:CompileCommand=compileonly,*VolatileTester.doVolatile()
     */
    private static volatile int counter = 0;

    public static void main(String[] args) {
        new Thread(VolatileTester::doVolatile, "t1").start();
    }

    public static void doVolatile() {
        counter++;
        /**
         *   0x000001b074e123c1: lock addl $0x0,-0x40(%rsp)  ;*putstatic counter {reexecute=0 rethrow=0 return_oop=0}
         *                                                 ; - com.photowey.juc.in.action.hsdis.VolatileTester::doVolatile@5 (line 29)                     ## line 29 没有加 license-header的 line-no
         *
         * src/hotspot/share/interpreter/bytecodeInterpreter.cpp:1975
         * <pre>
         * if (cache->is_volatile()) {
         *      if (support_IRIW_for_not_multiple_copy_atomic_cpu) {
         *          OrderAccess::fence();
         *      }
         *  ...
         * }
         * </pre>
         *<pre>
         * inline void OrderAccess::fence() {
         *    // always use locked addl since mfence is sometimes expensive
         * #ifdef AMD64
         *   __asm__ volatile ("lock; addl $0,0(%%rsp)" : : : "cc", "memory");        # __asm__: 内联汇编;  volatile: 防止 gcc 优化掉无用的代码; lock: 锁总线
         * #else
         *   __asm__ volatile ("lock; addl $0,0(%%esp)" : : : "cc", "memory");
         * #endif
         *   compiler_barrier();
         * }
         *</pre>
         */
    }
}
