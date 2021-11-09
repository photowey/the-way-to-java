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
package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.sql;

import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.IgnoreField;
import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableId;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code UpdateDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public class UpdateDynamicSql extends AbstractDynamicSql {

    @Override
    public String getId(Class<?> repository) {
        return this.concat(repository.getCanonicalName(), this.dynamicUpdate());
    }

    @Override
    public SqlCommandType getSqlCommandType() {
        return SqlCommandType.UPDATE;
    }

    @Override
    public SqlSource sqlSource(SqlSessionFactory sessionFactory, Class<?> generic) {
        String updateSql = this.handleUpdateSql(generic);
        List<SqlNode> contents = new ArrayList<>();
        contents.add(new StaticTextSqlNode(updateSql));
        contents.add(this.asTrimSqlNode(sessionFactory, generic));
        contents.add(new StaticTextSqlNode(this.whereSql(generic)));
        MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new DynamicSqlSource(sessionFactory.getConfiguration(), mixedSqlNode);

        return sqlSource;
    }

    @Override
    public String asIfContent(Field field) {
        String template = " %s = #{ %s }, ";
        return String.format(template, this.determineTableColumn(field), field.getName());
    }

    public String handleUpdateSql(Class<?> clazz) {
        String template = "UPDATE %s ";

        return String.format(template, this.determineTableName(clazz));
    }

    private String whereSql(Class<?> clazz) {
        String template = " WHERE %s = #{%s}";
        String sql = "";
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(IgnoreField.class)) {
                continue;
            }
            if (field.isAnnotationPresent(TableId.class)) {
                sql = String.format(template, field.getAnnotation(TableId.class).value(), field.getName());
                break;
            }
        }

        return sql;
    }

    private SqlNode asTrimSqlNode(SqlSessionFactory sqlSessionFactory, Class<?> clazz) {
        SqlNode sqlNode = this.handleIfMixedSqlNode(clazz);
        return new TrimSqlNode(sqlSessionFactory.getConfiguration(), sqlNode, "SET", null, null, ",");
    }
}
