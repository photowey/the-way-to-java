package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.sql;

import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.IgnoreField;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code DeleteDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public class DeleteDynamicSql extends AbstractDynamicSql {

    @Override
    public String getId(Class<?> repository) {
        return this.concat(repository.getCanonicalName(), this.dynamicDelete());
    }

    @Override
    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.DELETE;
    }

    @Override
    public SqlSource sqlSource(SqlSessionFactory sessionFactory, Class<?> generic) {
        String deleteSql = this.handleDeleteSql(generic);
        List<SqlNode> contents = new ArrayList<>();
        contents.add(new StaticTextSqlNode(deleteSql));
        contents.add(getTrimSqlNode(sessionFactory, generic));
        MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new DynamicSqlSource(sessionFactory.getConfiguration(), mixedSqlNode);

        return sqlSource;
    }

    public String handleDeleteSql(Class<?> clazz) {
        String template = "DELETE FROM %s ";
        String tableName = this.determineTableName(clazz);

        return String.format(template, tableName);
    }

    private SqlNode getTrimSqlNode(SqlSessionFactory sqlSessionFactory, Class<?> clazz) {
        SqlNode sqlNode = handlerIfMixedSqlNode(clazz);
        // <trim prefix="WHERE" prefixOverrides="AND|OR"></trim>
        return new TrimSqlNode(sqlSessionFactory.getConfiguration(), sqlNode, "WHERE", "AND|OR", null, null);
    }

    public SqlNode handlerIfMixedSqlNode(Class<?> clazz) {
        List<SqlNode> contents = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(IgnoreField.class)) {
                continue;
            }
            List<SqlNode> ifChildContents = new ArrayList<>();
            ifChildContents.add(new StaticTextSqlNode(this.asIfTagContent(field)));
            MixedSqlNode ifMixedSqlNode = new MixedSqlNode(ifChildContents);
            String ifTestTemplate = " %s != null and %s !='' ";
            IfSqlNode ifSqlNode = new IfSqlNode(ifMixedSqlNode, String.format(ifTestTemplate, field.getName(), field.getName()));
            contents.add(ifSqlNode);
        }

        return new MixedSqlNode(contents);
    }

    /**
     * 当作 If 标签的叶子节点
     * <if test="xxx != null and xxx != ''"></if>
     *
     * @param field 字段
     * @return delete SQL 片段
     */
    private String asIfTagContent(Field field) {
        String template = " AND %s = #{ %s } ";

        return String.format(template, this.determineTableColumn(field), field.getName());
    }
}
