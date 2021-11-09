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
package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.repository;

import java.util.List;

/**
 * {@code CrudRepository}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public interface CrudRepository<T> extends Repository<T> {

    // ========================================= 单对象操作

    /**
     * 单对象-插入
     *
     * @param po 数据库映射-持久化对象
     * @return {@link T}
     */
    int dynamicInsert(T po);

    /**
     * 单对象-更新
     *
     * @param po 数据库映射-持久化对象
     * @return {@link T}
     */
    int dynamicUpdate(T po);

    /**
     * 单对象-查询
     *
     * @param po 数据库映射-持久化对象
     * @return {@link List <T>}
     */
    List<T> dynamicSelect(T po);

    /**
     * 单对象-删除
     *
     * @param po 数据库映射-持久化对象
     * @return {@link T}
     */
    int dynamicDelete(T po);
}
