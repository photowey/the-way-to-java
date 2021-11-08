package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.repository;

import com.photowey.mybatis.in.action.mybatis.dynamic.kernel.criteria.Criteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * {@code CriteriaRepository}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public interface CriteriaRepository<T> extends CrudRepository<T> {

    /**
     * 条件查询
     *
     * @param criteria {@link Criteria}
     * @return {@link List<T>}
     */
    List<T> dynamicCriteria(@Param("criteria") Criteria criteria);
}
