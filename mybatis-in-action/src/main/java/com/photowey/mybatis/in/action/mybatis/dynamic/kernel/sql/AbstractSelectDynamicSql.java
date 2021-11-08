package com.photowey.mybatis.in.action.mybatis.dynamic.kernel.sql;

/**
 * {@code AbstractSelectDynamicSql}
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
public abstract class AbstractSelectDynamicSql extends AbstractDynamicSql {

    protected String handleSelectSql(Class<?> clazz) {
        String template = "SELECT %s FROM %s ";

        return String.format(template, this.determineSelectTableColumns(clazz), this.determineTableName(clazz));
    }


}
