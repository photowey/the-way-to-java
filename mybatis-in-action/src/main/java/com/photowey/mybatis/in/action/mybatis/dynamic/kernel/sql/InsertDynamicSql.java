package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.sql;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code InsertDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public class InsertDynamicSql extends AbstractDynamicSql {

    @Override
    public String getId(Class<?> repository) {
        return this.concat(repository.getCanonicalName(), this.dynamicInsert());
    }

    @Override
    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.INSERT;
    }

    @Override
    public SqlSource sqlSource(SqlSessionFactory sessionFactory, Class<?> generic) {
        String insertSQL = this.handlerInsertSql(generic);
        List<SqlNode> contents = new ArrayList<>();
        contents.add(new StaticTextSqlNode(insertSQL));
        MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new RawSqlSource(sessionFactory.getConfiguration(), mixedSqlNode, generic);

        return sqlSource;
    }

    public String handlerInsertSql(Class<?> clazz) {
        String template = "INSERT INTO %s %s VALUES %s ";
        String tableName = this.determineTableName(clazz);
        String columns = this.determineTableColumns(clazz);
        String fields = this.determineTableFields(clazz);

        return String.format(template, tableName, columns, fields);
    }
}
