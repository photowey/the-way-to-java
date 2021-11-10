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
package com.photowey.mybatis.in.action.mybatis.plugin;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Objects;

/**
 * {@code MybatisSamplePlugin}
 * 能够生成代理的四个对象
 *
 * @author photowey
 * @date 2021/11/07
 * @see {@link org.apache.ibatis.executor.Executor}
 * @see {@link StatementHandler}
 * @see {@link org.apache.ibatis.executor.parameter.ParameterHandler}
 * @see {@link org.apache.ibatis.executor.resultset.ResultSetHandler}
 * @since 1.0.0
 */
@Slf4j
@Data
@Intercepts(value = {
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})}
)
public class MybatisSamplePlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) {
            // RoutingStatementHandler
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            // log.info("the statementHandler value is:{}", statementHandler.getClass().getName());
            // BaseStatementHandler
            Field delegate = this.findField(statementHandler, "delegate");
            StatementHandler prepareStatement = (StatementHandler) delegate.get(statementHandler);
            // log.info("the prepareStatement value is:{}", prepareStatement.getClass().getName());

            Field boundSqlField = this.findField(prepareStatement, "boundSql");
            BoundSql boundSql = (BoundSql) boundSqlField.get(prepareStatement);

            Field sqlField = this.findField(boundSql, "sql");
            String sql = (String) sqlField.get(boundSql);
            log.info("\n" +
                            "-------------------------------------------------------------------------------\n{}" +
                            "\n-------------------------------------------------------------------------------",
                    sql.replaceAll("[\\t\\n\\r]", "").replaceAll("\\s+", " "));
        }

        return invocation.proceed();
    }

    private Field findField(Object target, String fieldName) {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        ReflectionUtils.makeAccessible(Objects.requireNonNull(field));
        return field;
    }
}
