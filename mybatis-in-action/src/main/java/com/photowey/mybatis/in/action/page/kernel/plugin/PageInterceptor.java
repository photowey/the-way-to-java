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
package com.photowey.mybatis.in.action.page.kernel.plugin;

import com.photowey.mybatis.in.action.page.Page;
import com.photowey.mybatis.in.action.page.PageUtils;
import com.photowey.mybatis.in.action.page.kernel.parser.CountSqlParser;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

/**
 * {@code PageInterceptor}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
@Intercepts({
        // 四参数
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        // 六参数
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class PageInterceptor implements Interceptor {

    private static final String PAGE_PARAM_OFFSET = "PAGE_PARAM_OFFSET";
    private static final String PAGE_PARAM_ROWS = "PAGE_PARAM_ROWS";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Page<Object> page = PageUtils.get();
        if (page == null) {
            return invocation.proceed();
        }

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        // 1.先查询总数
        Long totalCount = this.doCount(invocation, boundSql);
        page.setTotalCount(totalCount);
        if (totalCount <= 0) {
            page.setData(new ArrayList<>());
        } else {
            // 2.分页查询
            List<Object> data = this.doPageQuery(invocation, boundSql);
            page.setData(data);
        }

        // 清除 thread_local
        PageUtils.clear();

        return page;
    }

    private Long doCount(Invocation invocation, BoundSql boundSql) {
        // 拿到四参数
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
        ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];

        // 拿到执行器
        Executor executor = (Executor) invocation.getTarget();

        // 解析 Count SQL
        CountSqlParser countSqlParser = new CountSqlParser();
        String countSql = countSqlParser.getSmartCountSql(boundSql.getSql());

        // 创建 Count SQL 的新 BoundSql 对象
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
        // 添加 Count BoundSql 的实参,就是从查询中来的
        this.handleCloneBoundSqlAdditionalParameter(countBoundSql, boundSql);
        // 构建 CacheKey -> 准备调用六参数接口
        CacheKey cacheKey = executor.createCacheKey(mappedStatement, parameter, RowBounds.DEFAULT, boundSql);
        // 构建 MappedStatement
        MappedStatement countMappedStatement = this.populateCountMappedStatement(mappedStatement);
        try {
            // 执行 六参数 Count 查询
            List<Object> results = executor.query(countMappedStatement, parameter, rowBounds, resultHandler, cacheKey, countBoundSql);
            return (Long) results.get(0);

        } catch (SQLException e) {
            throw new RuntimeException("执行分页数量查询失败:" + countSql, e);
        }
    }

    /**
     * SELECT * FROM table_name WHERE xxx = ? LIMIT ?,?
     */
    private <E> List<E> doPageQuery(Invocation invocation, BoundSql boundSql) {
        // 获取四参数
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
        ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];

        // 拿到执行器
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey = executor.createCacheKey(mappedStatement, parameter, rowBounds, mappedStatement.getBoundSql(parameter));
        this.createParamMappingToBoundSql(mappedStatement, boundSql, cacheKey);
        this.createParamObjectToBoundSql(mappedStatement, boundSql);

        String limitSQL = this.concatLimitSql(boundSql);
        // 创建新的 BoundSql 对象
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), limitSQL, boundSql.getParameterMappings(), boundSql.getParameterObject());
        this.handleCloneBoundSqlAdditionalParameter(newBoundSql, boundSql);
        try {
            return executor.query(mappedStatement, parameter, rowBounds, resultHandler, cacheKey, newBoundSql);
        } catch (SQLException e) {
            throw new RuntimeException("执行分页列表查询失败", e);
        }
    }

    private void handleCloneBoundSqlAdditionalParameter(BoundSql newBoundSql, BoundSql oldBoundSql) {
        Field additionalParametersOld = ReflectionUtils.findField(oldBoundSql.getClass(), "additionalParameters");
        ReflectionUtils.makeAccessible(additionalParametersOld);
        Map<String, Object> additionalParameters = (Map<String, Object>) ReflectionUtils.getField(additionalParametersOld, oldBoundSql);
        Objects.requireNonNull(additionalParameters).forEach(newBoundSql::setAdditionalParameter);
    }

    private MappedStatement populateCountMappedStatement(MappedStatement ms) {
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "_COUNT_", ms.getSqlSource(), ms.getSqlCommandType())
                .resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .timeout(ms.getTimeout())
                .statementType(ms.getStatementType())
                .keyGenerator(ms.getKeyGenerator())
                .databaseId(ms.getDatabaseId())
                .lang(ms.getLang())
                .resultSetType(ms.getResultSetType())
                .flushCacheRequired(ms.isFlushCacheRequired())
                .useCache(ms.isUseCache());

        if (!ObjectUtils.isEmpty(ms.getKeyProperties())) {
            List<String> keyProperties = new ArrayList<>(Arrays.asList(ms.getKeyProperties()));
            statementBuilder.keyProperty(String.join(",", keyProperties));
        }
        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Long.class, new ArrayList<>()).build();
        resultMaps.add(resultMap);
        statementBuilder.resultMaps(resultMaps);
        MappedStatement statement = statementBuilder.build();

        return statement;
    }

    private void createParamMappingToBoundSql(MappedStatement mappedStatement, BoundSql boundSql, CacheKey cacheKey) {
        Page<?> page = PageUtils.get();
        cacheKey.update(page.getOffset());
        cacheKey.update(page.getPageSize());
        if (boundSql.getParameterMappings() != null) {
            if (boundSql.getParameterMappings().size() == 0) {
                Field parameterMappings = ReflectionUtils.findField(boundSql.getClass(), "parameterMappings");
                ReflectionUtils.makeAccessible(parameterMappings);
                ReflectionUtils.setField(parameterMappings, boundSql, new ArrayList<ParameterMapping>());
            }
            boundSql.getParameterMappings().add(new ParameterMapping.Builder(mappedStatement.getConfiguration(), PAGE_PARAM_OFFSET, Integer.class).build());
            boundSql.getParameterMappings().add(new ParameterMapping.Builder(mappedStatement.getConfiguration(), PAGE_PARAM_ROWS, Integer.class).build());
        }
    }

    private void createParamObjectToBoundSql(MappedStatement mappedStatement, BoundSql boundSql) {
        Page<?> page = PageUtils.get();
        Object parameterObject = boundSql.getParameterObject();
        MetaObject metaObject = mappedStatement.getConfiguration().newMetaObject(parameterObject);
        metaObject.setValue(PAGE_PARAM_OFFSET, page.getOffset());
        metaObject.setValue(PAGE_PARAM_ROWS, page.getPageSize());
    }

    private String concatLimitSql(BoundSql boundSql) {
        String sql = boundSql.getSql();
        sql += " LIMIT ?, ? ";

        return sql;
    }
}
