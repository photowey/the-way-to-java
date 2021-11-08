package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.sql;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * {@code DynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public interface DynamicSql {

    void handleDynamicSql(SqlSessionFactory sessionFactory, Class<?> generic, Class<?> repository);

    // =========================================

    String getId(Class<?> repository);

    SqlCommandType getSqlCommandType();

    SqlSource sqlSource(SqlSessionFactory sessionFactory, Class<?> generic);

    List<ResultMap> getResultMaps(SqlSessionFactory sessionFactory, Class<?> generic, Class<?> repository);

    // =========================================

    default String concat(String me, String you) {
        return me + you;
    }

    default String dynamicInsert() {
        return ".dynamicInsert";
    }

    default String dynamicDelete() {
        return ".dynamicDelete";
    }

    default String dynamicUpdate() {
        return ".dynamicUpdate";
    }

    default String dynamicSelect() {
        return ".dynamicSelect";
    }

    default String dynamicCriteria() {
        return ".dynamicCriteria";
    }
}