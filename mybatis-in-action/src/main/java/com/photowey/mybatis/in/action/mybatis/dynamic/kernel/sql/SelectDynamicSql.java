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
 * {@code SelectDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public class SelectDynamicSql extends AbstractSelectDynamicSql {

    @Override
    public String getId(Class<?> repository) {
        return this.concat(repository.getCanonicalName(), this.dynamicSelect());
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
        contents.add(this.asTrimSqlNode(sessionFactory, generic));
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


    private SqlNode asTrimSqlNode(SqlSessionFactory sqlSessionFactory, Class<?> clazz) {
        SqlNode sqlNode = this.handleIfMixedSqlNode(clazz);
        return new TrimSqlNode(sqlSessionFactory.getConfiguration(), sqlNode, "WHERE", "AND|OR", null, null);
    }
}
