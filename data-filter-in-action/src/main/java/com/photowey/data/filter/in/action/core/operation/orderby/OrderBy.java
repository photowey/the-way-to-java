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
package com.photowey.data.filter.in.action.core.operation.orderby;

import com.photowey.data.filter.in.action.util.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * {@code OrderBy}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
@AllArgsConstructor
public class OrderBy {

    private Condition condition;

    public List<Object> orderBy(List<Object> data) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>(1);
        }
        data.sort((you, me) -> {
            int ret = 0;
            for (int i = 0; i < condition.getFields().size(); i++) {
                ret = this.compareObject(condition.getFields().get(i), condition.getTypes().get(i), you, me);
                if (0 != ret) {
                    break;
                }
            }
            return ret;
        });

        return data;
    }

    private int compareObject(final String field, final boolean isAsc, Object you, Object me) {
        Object your = you instanceof Map ? ((Map) you).get(field) : ObjectUtils.getFieldValue(you, field);
        Object mine = me instanceof Map ? ((Map) me).get(field) : ObjectUtils.getFieldValue(me, field);

        if (your instanceof Number && mine instanceof Number) {
            return isAsc
                    ? new BigDecimal(your.toString()).compareTo(new BigDecimal(mine.toString()))
                    : new BigDecimal(mine.toString()).compareTo(new BigDecimal(your.toString()));
        } else if (your instanceof Date && mine instanceof Date) {
            long yourTime = ((Date) your).getTime();
            long mineTime = ((Date) mine).getTime();
            return isAsc
                    ? new BigDecimal(String.valueOf(yourTime)).compareTo(new BigDecimal(String.valueOf(mineTime)))
                    : new BigDecimal(String.valueOf(mineTime)).compareTo(new BigDecimal(String.valueOf(yourTime)));
        } else {
            return isAsc ? String.valueOf(your).compareTo(String.valueOf(mine)) : String.valueOf(mine).compareTo(String.valueOf(your));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Condition {
        List<String> fields;
        List<Boolean> types;
    }
}
