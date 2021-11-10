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
package com.photowey.spring.in.action.dynamic.handler.impl;

import com.photowey.spring.in.action.dynamic.constant.DynamicInjectedConstants;
import com.photowey.spring.in.action.dynamic.context.DynamicContext;
import com.photowey.spring.in.action.dynamic.context.KvPair;
import com.photowey.spring.in.action.dynamic.enums.DynamicAction;
import com.photowey.spring.in.action.dynamic.handler.DynamicInvokeHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * {@code BatchInvokeHandlerImpl}
 *
 * @author photowey
 * @date 2021/11/10
 * @since 1.0.0
 */
public class BatchInvokeHandlerImpl implements DynamicInvokeHandler, ApplicationContextAware {

    private static final String TARGET_BATCH_ACTION = DynamicAction.BATCH.name();

    private ApplicationContext applicationContext;

    @Override
    public boolean supports(String type) {
        return TARGET_BATCH_ACTION.equalsIgnoreCase(type);
    }

    @Override
    public Object invoke(Object bean, Method method, Object[] args, Class<?> targetType, String[] candidates) {
        Map<String, ?> beans = this.applicationContext.getBeansOfType(targetType);

        beans.forEach((k, v) -> {
            try {
                // 排除掉-自动生成的代理类
                boolean isDynamicAutoGenerateProxy = k.startsWith(DynamicInjectedConstants.BEAN_NAME_TEMPLATE_PREFIX);
                if (!isDynamicAutoGenerateProxy) {
                    System.out.println(k + "--" + v.getClass().getName());
                    Object result = method.invoke(v, args);
                    DynamicContext.add(new KvPair(k, result));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
