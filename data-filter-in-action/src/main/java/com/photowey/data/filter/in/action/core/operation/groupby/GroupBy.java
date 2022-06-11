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
package com.photowey.data.filter.in.action.core.operation.groupby;

import com.photowey.data.filter.in.action.core.enums.AggregationType;
import com.photowey.data.filter.in.action.core.operation.groupby.aggregation.MethodAggregation;
import com.photowey.data.filter.in.action.util.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * {@code GroupBy}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
@AllArgsConstructor
public class GroupBy {

    private Condition condition;

    public List<Object> groupBy(List<Object> candidates) {
        if (CollectionUtils.isEmpty(candidates)) {
            return new ArrayList<>(1);
        }

        Map<String, List<Object>> groupMap = this.populateGroupData(candidates);

        return this.aggregation(groupMap, condition);
    }

    private Map<String, List<Object>> populateGroupData(List<Object> candidates) {
        Map<String, List<Object>> groupByMap = new HashMap<>();
        for (Object target : candidates) {
            List<String> values = new ArrayList<>();
            condition.getFields().forEach((key) -> {
                values.add(ObjectUtils.getStringValue(ObjectUtils.getFieldValue(target, key)));
            });

            String groupKey = String.join(":", values);

            if (org.springframework.util.ObjectUtils.isEmpty(groupKey)) {
                throw new NullPointerException("分组字段不能为空!");
            }

            if (!groupByMap.containsKey(groupKey)) {
                List<Object> filterList = new ArrayList<>();
                filterList.add(target);
                groupByMap.put(groupKey, filterList);
            } else {
                groupByMap.get(groupKey).add(target);
            }
        }

        return groupByMap;
    }

    private List<Object> aggregation(Map<String, List<Object>> groupMap, Condition condition) {
        List<Object> context = new ArrayList<>();

        ServiceLoader<MethodAggregation> handlers = ServiceLoader.load(MethodAggregation.class);
        for (Map.Entry<String, List<Object>> valueEntry : groupMap.entrySet()) {

            Map<String, Object> combined = new HashMap<>();
            Object first = valueEntry.getValue().get(0);

            // 属性拷贝
            Field[] declaredFields = first.getClass().getDeclaredFields();
            Stream.of(declaredFields).forEach(field -> {
                combined.put(field.getName(), ObjectUtils.getFieldValue(first, field));
            });

            for (String key : condition.getFields()) {
                combined.put(key, ObjectUtils.getFieldValue(first, key));
            }
            for (AggregationType type : condition.getTypes()) {
                for (MethodAggregation handler : handlers) {
                    if (handler.support(type)) {
                        String aggregationResult = handler.handle(valueEntry, type);
                        combined.put(type.wrapper(), aggregationResult);
                    }
                }
            }
            context.add(combined);
        }

        return context;
    }

    @Getter
    @AllArgsConstructor
    public static class Condition implements Serializable {

        List<String> fields;
        List<AggregationType> types;
    }
}
