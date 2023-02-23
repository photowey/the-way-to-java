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
package com.photowey.jvm.in.action.policy;

/**
 * {@code NewPolicy}
 *
 * @author photowey
 * @date 2022/10/06
 * @since 1.0.0
 */
public class NewPolicy {

    /**
     * 内存分配策略
     * 1.优先分配到 Eden
     * 2.大对象直接分配到 老年代
     * - 尽量避免程序中出现过多的大对象
     * 3.长期存活的对象分配到 老年代
     * 4.动态对象年龄判断
     * - 如果 S 区中相同年龄的所有对象的总和大于 S 区空间的一半
     * -- 年龄 >= 该年龄的对象直接进入老年代
     * -- 无需等到 MaxTenuringThreshold 中 要求的年龄
     * 5.空间分配担保
     * -XX: HandlePromotionFailure
     */
    public static void main(String[] args) {

    }
}
