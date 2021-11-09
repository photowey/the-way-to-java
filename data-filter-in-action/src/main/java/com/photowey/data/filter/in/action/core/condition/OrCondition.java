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
package com.photowey.data.filter.in.action.core.condition;

import com.photowey.data.filter.in.action.core.context.Context;
import com.photowey.data.filter.in.action.pair.KvPari;
import com.photowey.data.filter.in.action.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * {@code OrCondition}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
public class OrCondition implements ICondition<String, Object> {

    public static final String OR_CONDITION = "or";

    @Override
    public boolean supports(String type) {
        return OR_CONDITION.equalsIgnoreCase(type);
    }

    @Override
    public boolean handle(Context<String, Object> context) {
        KvPari<String, Object> kvPair = context.kvPair();
        Object target = context.target();
        String field = ObjectUtils.getStringValue(kvPair.getKey());
        Object value = ObjectUtils.getFieldValue(target, field);

        if (value instanceof Number) {
            String targetValue = ObjectUtils.getStringValue(kvPair.getValue());
            return context.match() || new BigDecimal(ObjectUtils.getStringValue(value)).compareTo(new BigDecimal(targetValue)) == 0;
        }

        if (value instanceof Date) {
            long you = ((Date) kvPair.getValue()).getTime();
            long me = ((Date) value).getTime();
            return context.match() || you == me;
        }

        return context.match() || kvPair.getValue().equals(value);
    }
}
