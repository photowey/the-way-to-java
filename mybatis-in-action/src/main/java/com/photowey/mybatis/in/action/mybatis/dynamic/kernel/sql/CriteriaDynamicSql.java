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
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code CriteriaDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public class CriteriaDynamicSql extends AbstractSelectDynamicSql {

    @Override
    public String getId(Class<?> repository) {
        return this.concat(repository.getCanonicalName(), this.dynamicCriteria());
    }

    @Override
    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.SELECT;
    }

    @Override
    public SqlSource sqlSource(SqlSessionFactory sessionFactory, Class<?> generic) {
        String selectSql = this.handleSelectSql(generic);
        List<SqlNode> contents = new ArrayList<>();
        contents.add(new StaticTextSqlNode(selectSql));
        contents.add(this.asIfSqlNode(sessionFactory, generic));
        MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new DynamicSqlSource(sessionFactory.getConfiguration(), mixedSqlNode);

        return sqlSource;
    }

    @Override
    public List<ResultMap> getResultMaps(SqlSessionFactory sessionFactory, Class<?> generic, Class<?> repository) {
        String id = this.getResultId(generic, repository);
        ResultMap resultMap = sessionFactory.getConfiguration().getResultMap(id);
        return Collections.singletonList(resultMap);
    }

    private SqlNode asIfSqlNode(SqlSessionFactory sessionFactory, Class<?> generic) {
        List<SqlNode> ifChildContents = new ArrayList<>();
        ifChildContents.add(new TextSqlNode(" ${criteria.conditionSql} "));
        MixedSqlNode ifMixedSqlNode = new MixedSqlNode(ifChildContents);
        IfSqlNode ifSqlNode = new IfSqlNode(ifMixedSqlNode, "criteria != null and criteria.conditionSql != null ");

        return ifSqlNode;
    }
}
