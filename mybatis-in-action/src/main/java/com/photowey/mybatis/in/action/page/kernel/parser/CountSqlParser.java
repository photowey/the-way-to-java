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
package com.photowey.mybatis.in.action.page.kernel.parser;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * {@code CountSqlParser}
 * This code copy from 《享学课堂》 `Jack` 老师 `Mybatis` 代码
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
public class CountSqlParser {

    public static final String KEEP_ORDER_BY = "/*keep orderby*/";
    private static final Alias TABLE_ALIAS;

    // <editor-fold desc="聚合函数">
    private final Set<String> skipFunctions = Collections.synchronizedSet(new HashSet<>());
    private final Set<String> falseFunctions = Collections.synchronizedSet(new HashSet<>());

    /**
     * 聚合函数,以下列函数开头的都认为是聚合函数
     */
    private static final Set<String> AGGREGATE_FUNCTIONS = new HashSet<String>(Arrays.asList(
            ("APPROX_COUNT_DISTINCT," +
                    "ARRAY_AGG," +
                    "AVG," +
                    "BIT_" +
                    //"BIT_AND," +
                    //"BIT_OR," +
                    //"BIT_XOR," +
                    "BOOL_," +
                    //"BOOL_AND," +
                    //"BOOL_OR," +
                    "CHECKSUM_AGG," +
                    "COLLECT," +
                    "CORR," +
                    //"CORR_," +
                    //"CORRELATION," +
                    "COUNT," +
                    //"COUNT_BIG," +
                    "COVAR," +
                    //"COVAR_POP," +
                    //"COVAR_SAMP," +
                    //"COVARIANCE," +
                    //"COVARIANCE_SAMP," +
                    "CUME_DIST," +
                    "DENSE_RANK," +
                    "EVERY," +
                    "FIRST," +
                    "GROUP," +
                    //"GROUP_CONCAT," +
                    //"GROUP_ID," +
                    //"GROUPING," +
                    //"GROUPING," +
                    //"GROUPING_ID," +
                    "JSON_," +
                    //"JSON_AGG," +
                    //"JSON_ARRAYAGG," +
                    //"JSON_OBJECT_AGG," +
                    //"JSON_OBJECTAGG," +
                    //"JSONB_AGG," +
                    //"JSONB_OBJECT_AGG," +
                    "LAST," +
                    "LISTAGG," +
                    "MAX," +
                    "MEDIAN," +
                    "MIN," +
                    "PERCENT_," +
                    //"PERCENT_RANK," +
                    //"PERCENTILE_CONT," +
                    //"PERCENTILE_DISC," +
                    "RANK," +
                    "REGR_," +
                    "SELECTIVITY," +
                    "STATS_," +
                    //"STATS_BINOMIAL_TEST," +
                    //"STATS_CROSSTAB," +
                    //"STATS_F_TEST," +
                    //"STATS_KS_TEST," +
                    //"STATS_MODE," +
                    //"STATS_MW_TEST," +
                    //"STATS_ONE_WAY_ANOVA," +
                    //"STATS_T_TEST_*," +
                    //"STATS_WSR_TEST," +
                    "STD," +
                    //"STDDEV," +
                    //"STDDEV_POP," +
                    //"STDDEV_SAMP," +
                    //"STDDEV_SAMP," +
                    //"STDEV," +
                    //"STDEVP," +
                    "STRING_AGG," +
                    "SUM," +
                    "SYS_OP_ZONE_ID," +
                    "SYS_XMLAGG," +
                    "VAR," +
                    //"VAR_POP," +
                    //"VAR_SAMP," +
                    //"VARIANCE," +
                    //"VARIANCE_SAMP," +
                    //"VARP," +
                    "XMLAGG").split(",")));
    // </editor-fold>

    static {
        TABLE_ALIAS = new Alias("table_count");
        TABLE_ALIAS.setUseAs(false);
    }

    /**
     * 添加到聚合函数,可以是逗号隔开的多个函数前缀
     *
     * @param functions
     */
    public static void addAggregateFunctions(String functions) {
        if (StringUtils.hasText(functions)) {
            String[] funs = functions.split(",");
            for (int i = 0; i < funs.length; i++) {
                AGGREGATE_FUNCTIONS.add(funs[i].toUpperCase());
            }
        }
    }

    /**
     * 获取智能的 Count Sql
     *
     * @param sql 原查询 sql
     * @return Count Sql
     */
    public String getSmartCountSql(String sql) {
        return this.getSmartCountSql(sql, "*");
    }

    /**
     * 获取智能的 Count Sql
     *
     * @param sql  原查询 sql
     * @param name 列名, 默认 *
     * @return
     */
    public String getSmartCountSql(String sql, String name) {
        // 解析SQL
        Statement stmt = null;
        // 特殊 sql 不 需要去掉 order by 时,使用注释前缀
        if (sql.contains(KEEP_ORDER_BY)) {
            return this.getSimpleCountSql(sql);
        }
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (Throwable e) {
            // 无法解析的用一般方法返回 count 语句
            return this.getSimpleCountSql(sql);
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        try {
            // 处理 body --去 order by
            this.processSelectBody(selectBody);
        } catch (Exception e) {
            // 当 sql 包含 group by 时,不去除 order by
            return this.getSimpleCountSql(sql);
        }
        // 处理 with --去 order by
        this.processWithItemsList(select.getWithItemsList());
        // 处理为 count 查询
        this.sqlToCount(select, name);
        String result = select.toString();

        return result;
    }

    /**
     * 获取普通的 Count-sql
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    public String getSimpleCountSql(final String sql) {
        return getSimpleCountSql(sql, "*");
    }

    /**
     * 获取普通的Count-sql
     *
     * @param sql 原查询 sql
     * @return 返回count查询sql
     */
    public String getSimpleCountSql(final String sql, String name) {
        StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
        stringBuilder.append("SELECT COUNT(");
        stringBuilder.append(name);
        stringBuilder.append(") FROM (");
        stringBuilder.append(sql);
        stringBuilder.append(") tmp_count");

        return stringBuilder.toString();
    }

    /**
     * 将sql转换为count查询
     *
     * @param select
     */
    public void sqlToCount(Select select, String name) {
        SelectBody selectBody = select.getSelectBody();
        // 是否能简化 COUNT 查询
        List<SelectItem> COUNT_ITEM = new ArrayList<>();
        COUNT_ITEM.add(new SelectExpressionItem(new Column("COUNT(" + name + ")")));
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect) selectBody)) {
            ((PlainSelect) selectBody).setSelectItems(COUNT_ITEM);
        } else {
            PlainSelect plainSelect = new PlainSelect();
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(COUNT_ITEM);
            select.setSelectBody(plainSelect);
        }
    }

    /**
     * 是否可以用简单的 Count 查询方式
     *
     * @param select
     * @return
     */
    public boolean isSimpleCount(PlainSelect select) {
        // 包含 Group By 的时候不可以
        if (select.getGroupBy() != null && !CollectionUtils.isEmpty(select.getGroupBy().getGroupByExpressions())) {
            return false;
        }
        // 包含 DISTINCT 的时候不可以
        if (select.getDistinct() != null) {
            return false;
        }
        for (SelectItem item : select.getSelectItems()) {
            // Select 列中包含参数的时候不可以,否则会引起参数个数错误
            if (item.toString().contains("?")) {
                return false;
            }
            // 如果查询列中包含函数,也不可以,函数可能会聚合列
            if (item instanceof SelectExpressionItem) {
                Expression expression = ((SelectExpressionItem) item).getExpression();
                if (expression instanceof Function) {
                    String name = ((Function) expression).getName();
                    if (name != null) {
                        String NAME = name.toUpperCase();
                        if (skipFunctions.contains(NAME)) {
                            // go on
                        } else if (falseFunctions.contains(NAME)) {
                            return false;
                        } else {
                            for (String aggregateFunction : AGGREGATE_FUNCTIONS) {
                                if (NAME.startsWith(aggregateFunction)) {
                                    falseFunctions.add(NAME);
                                    return false;
                                }
                            }
                            skipFunctions.add(NAME);
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * 处理 Select Body 去除 Order By
     *
     * @param selectBody
     */
    public void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            this.processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                this.processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                List<SelectBody> plainSelects = operationList.getSelects();
                for (SelectBody plainSelect : plainSelects) {
                    this.processSelectBody(plainSelect);
                }
            }
            if (!this.orderByHashParameters(operationList.getOrderByElements())) {
                operationList.setOrderByElements(null);
            }
        }
    }

    /**
     * 处理 PlainSelect 类型的 SelectBody
     *
     * @param plainSelect
     */
    public void processPlainSelect(PlainSelect plainSelect) {
        if (!this.orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            this.processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    this.processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 处理 WithItem
     *
     * @param withItemsList
     */
    public void processWithItemsList(List<WithItem> withItemsList) {
        if (withItemsList != null && withItemsList.size() > 0) {
            for (WithItem item : withItemsList) {
                this.processSelectBody(item.getSelectBody());
            }
        }
    }

    /**
     * 处理子查询
     *
     * @param fromItem
     */
    public void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null && subJoin.getJoinList().size() > 0) {
                for (Join join : subJoin.getJoinList()) {
                    if (join.getRightItem() != null) {
                        this.processFromItem(join.getRightItem());
                    }
                }
            }
            if (subJoin.getLeft() != null) {
                this.processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                this.processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {

        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    this.processSelectBody(subSelect.getSelectBody());
                }
            }
        }
    }

    /**
     * 判断 Orderby 是否包含参数,有参数的不能去
     *
     * @param orderByElements
     * @return
     */
    public boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().contains("?")) {
                return true;
            }
        }

        return false;
    }
}
