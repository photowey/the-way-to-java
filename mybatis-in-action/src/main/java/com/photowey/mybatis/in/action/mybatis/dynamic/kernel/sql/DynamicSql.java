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