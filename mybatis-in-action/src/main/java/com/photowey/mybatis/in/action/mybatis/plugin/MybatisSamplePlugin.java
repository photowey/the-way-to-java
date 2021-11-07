package com.photowey.mybatis.in.action.mybatis.plugin;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

/**
 * {@code MybatisPluginSample}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class MybatisPluginSample implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.getTarget();
    }
}
