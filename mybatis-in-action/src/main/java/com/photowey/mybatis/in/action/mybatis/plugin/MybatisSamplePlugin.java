package com.photowey.mybatis.in.action.mybatis.plugin;

import lombok.Data;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * {@code MybatisSamplePlugin}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
@Data
@Intercepts(value = {
  @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})}
)
public class MybatisSamplePlugin implements Interceptor {

  // Inject by mybatis
  private String name;

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    return invocation.proceed();
  }
}
