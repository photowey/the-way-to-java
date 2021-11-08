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
