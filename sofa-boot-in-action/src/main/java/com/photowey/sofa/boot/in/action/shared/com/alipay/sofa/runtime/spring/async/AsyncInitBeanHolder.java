/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.sofa.boot.in.action.shared.com.alipay.sofa.runtime.spring.async;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author qilong.zql
 * @author xuanbei
 * @since 2.6.0
 */
public class AsyncInitBeanHolder {
    private static final ConcurrentMap<String, Map<String, String>> ASYNC_BEAN_INFOS = new ConcurrentHashMap<>();

    public static void registerAsyncInitBean(String moduleName, String beanId, String methodName) {
        if (moduleName == null || beanId == null || methodName == null) {
            return;
        }

        Map<String, String> asyncBeanInfosInModule = ASYNC_BEAN_INFOS.get(moduleName);
        if (asyncBeanInfosInModule == null) {
            ASYNC_BEAN_INFOS.putIfAbsent(moduleName, new ConcurrentHashMap<>());
            asyncBeanInfosInModule = ASYNC_BEAN_INFOS.get(moduleName);
        }

        asyncBeanInfosInModule.put(beanId, methodName);
    }

    public static String getAsyncInitMethodName(String moduleName, String beanId) {
        Map<String, String> asyncBeanInfosInModule = (moduleName == null) ? null : ASYNC_BEAN_INFOS.get(moduleName);
        return (beanId == null || asyncBeanInfosInModule == null) ? null : asyncBeanInfosInModule.get(beanId);
    }
}
