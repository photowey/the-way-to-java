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

import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.IgnoreField;
import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField;
import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableId;
import com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableName;
import com.photowey.mybatis.in.action.mybatis.dynamic.enums.IdTypeEnum;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * {@code AbstractDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public abstract class AbstractDynamicSql implements DynamicSql {

    @Override
    public void handleDynamicSql(SqlSessionFactory sessionFactory, Class<?> generic, Class<?> repository) {
        Configuration configuration = sessionFactory.getConfiguration();
        // 没有解析过: ResultMap
        if (!configuration.hasResultMap(this.getResultId(generic, repository))) {
            // 解析 ResultMap
            this.populateResultMap(sessionFactory, generic, repository);
        }
        // 注册:MappedStatement
        this.mappedStatement(sessionFactory, generic, repository);

        // 没有注册过: Repository
        if (!sessionFactory.getConfiguration().hasMapper(repository)) {
            sessionFactory.getConfiguration().addMapper(repository);
        }
    }

    /**
     * {@link ResultMap} 结果集
     *
     * @param sessionFactory {@link SqlSessionFactory}
     * @param generic        持久类
     * @param repository     {@link com.photowey.mybatis.in.action.mybatis.dynamic.kernel.repository.Repository} 根接口
     * @return {@link List<ResultMap>}
     */
    @Override
    public List<ResultMap> getResultMaps(SqlSessionFactory sessionFactory, Class<?> generic, Class<?> repository) {
        return new ArrayList<>();
    }

    public String getResultId(Class<?> generic, Class<?> repository) {
        return repository.getCanonicalName() + "." + generic.getSimpleName() + "Dynamic";
    }

    private Class<?> resolveResultJavaType(Class<?> resultType, String property, Class<?> javaType, SqlSessionFactory sessionFactory) {
        if (javaType == null && property != null) {
            try {
                MetaClass metaResultType = MetaClass.forClass(resultType, sessionFactory.getConfiguration().getReflectorFactory());
                javaType = metaResultType.getSetterType(property);
            } catch (Exception e) {
                //ignore, following null check statement will deal with the situation
            }
        }
        if (javaType == null) {
            javaType = Object.class;
        }

        return javaType;
    }

    private void populateResultMap(SqlSessionFactory sessionFactory, Class<?> clazz, Class<?> repository) {
        List<ResultMapping> resultMappings = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(IgnoreField.class)) {
                continue;
            }
            List<ResultFlag> flags = new ArrayList<>();
            if (field.isAnnotationPresent(TableId.class)) {
                flags.add(ResultFlag.ID);
            }
            String property = field.getName();
            String tableColumn = this.determineTableColumn(field);
            Class<?> javaType = this.resolveResultJavaType(clazz, field.getName(), null, sessionFactory);
            ResultMapping resultMapping = new ResultMapping.Builder(sessionFactory.getConfiguration(), property, tableColumn, javaType)
                    .jdbcType(null)
                    .nestedQueryId(null)
                    .nestedResultMapId(null)
                    .resultSet(null)
                    .typeHandler(null)
                    .flags(flags)
                    .composites(Collections.emptyList())
                    .notNullColumns(new HashSet<>())
                    .columnPrefix(null)
                    .foreignColumn(null)
                    .lazy(false)
                    .build();


            resultMappings.add(resultMapping);
        }
        String id = getResultId(clazz, repository);
        ResultMap resultMap = new ResultMap.Builder(sessionFactory.getConfiguration(), id, clazz, resultMappings, null)
                .discriminator(null)
                .build();
        sessionFactory.getConfiguration().addResultMap(resultMap);
    }

    public MappedStatement mappedStatement(SqlSessionFactory sessionFactory, Class generic, Class<?> repository) {
        boolean isAuto = false;
        String keyProperty = "";
        for (Field field : generic.getDeclaredFields()) {
            if (field.isAnnotationPresent(TableId.class)) {
                TableId tableId = field.getAnnotation(TableId.class);
                IdTypeEnum type = tableId.type();
                // TODO 基于插件的 Id 填充
                isAuto = IdTypeEnum.AUTO.equals(type);
                keyProperty = tableId.value();
                break;
            }
        }
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(sessionFactory.getConfiguration(), getId(repository), sqlSource(sessionFactory, generic), getSqlCommandType())
                .resource(null)
                .fetchSize(null)
                .timeout(null)
                .statementType(StatementType.PREPARED)
                .keyGenerator(isAuto ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE)
                .keyProperty(isAuto ? keyProperty : null)
                .keyColumn(null)
                .databaseId(null)
                .lang(sessionFactory.getConfiguration().getLanguageDriver(null))
                .resultOrdered(false)
                .resultSets(null)
                .resultMaps(getResultMaps(sessionFactory, generic, repository))
                .resultSetType(null)
                .flushCacheRequired(true)
                .useCache(false)
                .cache(null);
        MappedStatement statement = statementBuilder.build();
        sessionFactory.getConfiguration().addMappedStatement(statement);

        return statement;
    }

    /**
     * 获取-持久化对象-映射的数据表-表明
     *
     * @param poClazz poClazz 持久化-对象
     * @return 数据表-表名
     */
    protected String determineTableName(Class<?> poClazz) {
        TableName poClazzAnnotation = poClazz.getAnnotation(TableName.class);
        if (poClazzAnnotation != null) {
            return poClazzAnnotation.value();
        }

        return poClazz.getSimpleName();
    }

    protected String determineTableColumn(Field field) {
        // 判断是不是:主键
        String primaryKey = this.handleIdColumn(field);
        if (StringUtils.hasText(primaryKey)) {
            return primaryKey;
        }
        // 如果有 {@code @TableField} 注解
        TableField tableField = field.getAnnotation(TableField.class);
        if (tableField != null) {
            return tableField.value();
        }

        // 默认采用: 属性名
        // 可以设计为掉过该字段,即:不添加注解即为跳过==TableIgnore

        return field.getName();
    }

    protected String handleIdColumn(Field field) {
        String column = "";
        TableId tableId = field.getAnnotation(TableId.class);
        if (tableId == null) {
            return column;
        }
        switch (tableId.type()) {
            case AUTO:
                break;
            default:
                column = tableId.value();
        }

        return column;
    }

    protected String determineTableFields(Class<?> poClazz) {
        List<String> fields = new ArrayList<>();
        for (Field field : poClazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(IgnoreField.class)) {
                continue;
            }
            fields.add(this.wrapperColumn(field.getName()));
        }

        return "(" + String.join(",", fields) + ")";
    }

    protected String determineTableColumns(Class<?> poClazz) {
        return "(" + this.determineSelectTableColumns(poClazz) + ")";
    }

    protected String determineSelectTableColumns(Class<?> poClazz) {
        List<String> fields = new ArrayList<>();
        for (Field field : poClazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(IgnoreField.class)) {
                continue;
            }
            fields.add(this.determineTableColumn(field));
        }

        return String.join(",", fields);
    }

    protected String wrapperColumn(String source) {
        return "#{" + source + "}";
    }

    protected String asIfContent(Field field) {
        String template = " AND %s = #{ %s } ";
        return String.format(template, this.determineTableColumn(field), field.getName());
    }

    protected SqlNode handleIfMixedSqlNode(Class<?> clazz) {
        List<SqlNode> contents = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(IgnoreField.class)) {
                continue;
            }
            List<SqlNode> ifChildContents = new ArrayList<>();
            ifChildContents.add(new StaticTextSqlNode(this.asIfContent(field)));
            MixedSqlNode ifMixedSqlNode = new MixedSqlNode(ifChildContents);
            String ifTestTemplate = " %s != null and %s !='' ";

            IfSqlNode ifSqlNode = new IfSqlNode(ifMixedSqlNode, String.format(ifTestTemplate, field.getName(), field.getName()));
            contents.add(ifSqlNode);
        }

        return new MixedSqlNode(contents);
    }
}
