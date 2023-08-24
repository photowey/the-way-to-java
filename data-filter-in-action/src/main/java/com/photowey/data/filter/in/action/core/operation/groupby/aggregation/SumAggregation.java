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
package com.photowey.data.filter.in.action.core.operation.groupby.aggregation;

import com.photowey.data.filter.in.action.core.enums.AggregationType;
import com.photowey.data.filter.in.action.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * {@code SumAggregation}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
public class SumAggregation implements MethodAggregation<String, Object> {

    @Override
    public boolean support(AggregationType type) {
        return AggregationType.SUM.equals(type);
    }

    @Override
    public String handle(Map.Entry<String, List<Object>> context, AggregationType type) {
        List<Object> candidates = context.getValue();
        if (0 == candidates.size()) {
            return "0";
        }
        String field = type.field();
        Long sum = candidates.stream()
                .map((candidate) -> Long.parseLong(toNumberString(field, candidate)))
                .reduce(0L, Long::sum);

        return String.valueOf(sum);
    }

    private String toNumberString(String field, Object candidate) {
        Object number = Objects.requireNonNull(ObjectUtils.getNumberFieldValue(candidate, field, 0L));
        return String.valueOf(number);
    }

}
