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
package com.photowey.webservice.core.in.action.proxy.objectmapper;

import java.util.List;

/**
 * {@code ObjectMapperProxy}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/10/06
 */
public interface ObjectMapperProxy {

    <T> String toJSONString(T t);

    <T> T parseObject(String json, Class<T> clazz);

    <T> List<T> parseArray(String json, Class<T> clazz);

    <T> String toXMLString(T t);

    <T> T parseXMLObject(String xml, Class<T> clazz);

    <T> List<T> parseXMLArray(String xml, Class<T> clazz);
}
