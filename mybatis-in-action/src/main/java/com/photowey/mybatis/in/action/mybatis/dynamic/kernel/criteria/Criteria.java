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
