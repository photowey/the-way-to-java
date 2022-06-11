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
package com.photowey.data.filter.in.action.query;

import com.photowey.data.filter.in.action.core.operation.groupby.GroupBy;
import com.photowey.data.filter.in.action.core.operation.limit.Limit;
import com.photowey.data.filter.in.action.core.operation.orderby.OrderBy;
import com.photowey.data.filter.in.action.core.operation.where.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code JavaQuery}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
public class JavaQuery implements Serializable {

    public List<Object> query(List<Object> data, Where where, OrderBy orderBy, GroupBy groupBy, Limit limit) {
        List<Object> candidates = this.handleWhereCondition(data, where);
        candidates = this.handleGroupByCondition(candidates, groupBy);
        candidates = this.handleOrderByCondition(candidates, orderBy);
        candidates = this.handleLimitCondition(candidates, limit);

        return candidates;
    }

    private List<Object> handleWhereCondition(List<Object> candidates, Where where) {
        if (where == null) {
            return candidates;
        }
        List<Object> filterList = new ArrayList<>();
        for (Object candidate : candidates) {
            if (where.where(candidate)) {
                filterList.add(candidate);
            }
        }

        return filterList;
    }

    private List<Object> handleOrderByCondition(List<Object> candidates, OrderBy orderBy) {
        if (candidates.size() == 0 || orderBy == null) {
            return candidates;
        }

        return orderBy.orderBy(candidates);
    }

    private List<Object> handleGroupByCondition(List<Object> candidates, GroupBy groupBy) {
        if (candidates.size() == 0 || groupBy == null) {
            return candidates;
        }

        return groupBy.groupBy(candidates);
    }

    private List<Object> handleLimitCondition(List<Object> candidates, Limit limit) {
        if (candidates.size() == 0 || limit == null) {
            return candidates;
        }

        return limit.limit(candidates);
    }
}
