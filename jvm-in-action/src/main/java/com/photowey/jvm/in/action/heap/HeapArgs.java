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
package com.photowey.jvm.in.action.heap;

/**
 * {@code HeapArgs}
 *
 * @author photowey
 * @date 2022/10/06
 * @since 1.0.0
 */
public class HeapArgs {

    /**
     * -XX:+PrintFlagsInitial: 查看所有的参数的默认初始值
     * XX:+PrintFlagsFinal: 查看所有的参数的最终值(可能会存在修改,不再是初始值)
     * -- 查看
     * ---- $ jinfo <option> <pid>
     * ---- $ jinfo -flag SurvivorRatio 9527
     * <p>
     * -Xms: 初始堆空间内存(默认为物理内存的1/64)
     * -Xmx: 最大堆空间内存(默认为物理内存的1/4)
     * -Xmn: 设置新生代的大小(初始值及最大值)
     * -XX:NewRatio: 配置新生代与老年代在堆结构的占比
     * -XX:SurvivorRatio: 设置新生代中 Eden 和 S0/S1 空间的比例
     * -XX:MaxTenuringThreshold: 设置新生代垃圾的最大年龄
     * -XX:+PrintGCDetails: 输出详细的GC处理日志
     * -- 简要GC: -XX:+PrintGC / -verbose:gc
     * -XX:HandlePromotionFailure: 是否设置空间分配担保
     *
     * @param args
     * @see * https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html
     */
    public static void main(String[] args) {

    }

}
