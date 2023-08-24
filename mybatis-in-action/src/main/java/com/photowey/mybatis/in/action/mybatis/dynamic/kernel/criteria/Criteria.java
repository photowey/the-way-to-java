/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.criteria;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code Criteria}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public class Criteria {

    /**
     * 存储字段和值的映射
     */
    @Getter
    private static final Map<String, Object> conditionMap = new HashMap<>();

    private static final String PARAMETER_NAME = "__PARAMETER_NAME__";
    private static final AtomicInteger PARAMETER_INDEX = new AtomicInteger();
    private static final String PREFIX = "#{criteria.conditionMap." + PARAMETER_NAME;
    private static final String SUFFIX = "}";
    private static final String AND = " AND ";
    private static final String OR = " OR ";

    /**
     * 存储条件 sql 片段
     */
    private static final List<String> WHERE_CONDITION = new ArrayList<>();
    private static final List<String> GROUP_BY_CONDITION = new ArrayList<>();
    private static final List<String> HAVING_CONDITION = new ArrayList<>();
    private static final List<String> ORDER_BY_CONDITION = new ArrayList<>();

    public Criteria eq(String column, Object parameter) {
        this.segment(column, parameter, " = ");
        return this;
    }

    public Criteria ge(String column, Object parameter) {
        this.segment(column, parameter, " >= ");
        return this;
    }

    public Criteria le(String column, Object parameter) {
        this.segment(column, parameter, " <= ");
        return this;
    }

    public Criteria ne(String column, Object parameter) {
        this.segment(column, parameter, " <> ");
        return this;
    }

    public Criteria exists(String sql) {
        WHERE_CONDITION.add(" EXISTS (" + sql + ")");
        return this;
    }

    public Criteria groupBy(String columns) {
        GROUP_BY_CONDITION.add(" GROUP BY " + columns);
        return this;
    }

    public Criteria having(String sql) {
        HAVING_CONDITION.add(" HAVING " + sql);
        return this;
    }

    public Criteria orderBy(String sql) {
        ORDER_BY_CONDITION.add(" ORDER BY " + sql);
        return this;
    }

    public Criteria and() {
        WHERE_CONDITION.add(AND);
        return this;
    }

    public Criteria or() {
        WHERE_CONDITION.add(OR);
        return this;
    }

    private String segment(String column, Object parameters, String op) {
        int i = PARAMETER_INDEX.incrementAndGet();
        String segment = column + op + PREFIX + i + SUFFIX;
        WHERE_CONDITION.add(segment);

        conditionMap.put(PARAMETER_NAME + i, parameters);

        return segment;
    }

    private void whereSql(StringBuffer segment) {
        if (WHERE_CONDITION.size() > 0) {
            segment.append(" WHERE ");
            for (String condition : WHERE_CONDITION) {
                segment.append(condition);
            }
        }
    }

    private void groupBySql(StringBuffer segment) {
        if (GROUP_BY_CONDITION.size() > 0) {
            for (String condition : GROUP_BY_CONDITION) {
                segment.append(condition);
            }
        }
    }

    private void havingSql(StringBuffer segment) {
        if (HAVING_CONDITION.size() > 0) {
            for (String condition : HAVING_CONDITION) {
                segment.append(condition);
            }
        }
    }

    private void orderBySql(StringBuffer segment) {
        if (ORDER_BY_CONDITION.size() > 0) {
            for (String condition : ORDER_BY_CONDITION) {
                segment.append(segment);
            }
        }
    }

    public String getConditionSql() {
        StringBuffer SQL = new StringBuffer();
        this.whereSql(SQL);
        this.groupBySql(SQL);
        this.havingSql(SQL);
        this.orderBySql(SQL);

        return SQL.toString();
    }


    public static Criteria create() {
        return new Criteria();
    }
}
