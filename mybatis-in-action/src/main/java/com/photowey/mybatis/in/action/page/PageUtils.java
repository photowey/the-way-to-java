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
package com.photowey.mybatis.in.action.page;

/**
 * {@code PageUtils}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
public final class PageUtils {

    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL = new ThreadLocal<>();

    public static <T> Page<T> walk(int pageNo, int pageSize) {
        Page<T> page = new Page<>(pageNo, pageSize);
        set(page);
        return page;
    }

    public static <T> void set(Page<T> page) {
        PAGE_THREAD_LOCAL.set(page);
    }

    public static <T> Page<T> get() {
        return PAGE_THREAD_LOCAL.get();
    }

    public static void clear() {
        PAGE_THREAD_LOCAL.remove();
    }
}
