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
